package com.diskusage.domain.usecases.chart

import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.MaxSmallArcsDepth
import com.diskusage.domain.di.DomainModule
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.diskentry.GetDepth
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import java.nio.file.Path

class IncludeDiskEntryTest : KoinTest {

    private val includeDiskEntry by inject<IncludeDiskEntry>()
    private lateinit var getDepth: GetDepth

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(DomainModule().module)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `typical disk entry`() {
        includeDiskEntry.invoke(DiskEntryStubs.dir2) shouldBe true
        includeDiskEntry.invoke(DiskEntryStubs.file111, DiskEntryStubs.rootDir) shouldBe true
    }

    @Test
    fun `too deep disk entry`() {
        getDepth = declareMock {
            every {
                this@declareMock(
                    DiskEntryStubs.dir1,
                    DiskEntryStubs.rootDir
                )
            } returns MaxBigArcsDepth + MaxSmallArcsDepth + 1
        }
        includeDiskEntry.invoke(DiskEntryStubs.dir1) shouldBe false
        includeDiskEntry.invoke(DiskEntryStubs.dir1, DiskEntryStubs.rootDir) shouldBe false
    }

    @Test
    fun `too small disk entry`() {
        val fooFile = DiskEntry.File(
            name = "foo",
            path = Path.of("/dir/dir1/dir11/foo"),
            parent = DiskEntryStubs.dir11,
            sizeOnDisk = 32,
        )
        includeDiskEntry.invoke(fooFile) shouldBe false
        includeDiskEntry.invoke(fooFile, DiskEntryStubs.rootDir) shouldBe false
    }
}
