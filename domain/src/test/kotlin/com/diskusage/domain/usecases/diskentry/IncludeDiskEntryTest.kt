package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.common.Constants.Chart.MaxArcsDepth
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.stubs.DiskEntries
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import java.nio.file.Path

class IncludeDiskEntryTest : KoinTest {

    private val includeDiskEntry by inject<IncludeDiskEntry>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
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
        includeDiskEntry.invoke(DiskEntries.dir2) shouldBe true
        includeDiskEntry.invoke(DiskEntries.file111, DiskEntries.rootDir) shouldBe true
    }

    @Test
    fun `too deep disk entry`() {
        declareMock<GetDepth> {
            every {
                this@declareMock(
                    DiskEntries.dir1,
                    DiskEntries.rootDir
                )
            } returns MaxArcsDepth + 1
        }
        includeDiskEntry.invoke(DiskEntries.dir1) shouldBe false
        includeDiskEntry.invoke(DiskEntries.dir1, DiskEntries.rootDir) shouldBe false
    }

    @Test
    fun `too small disk entry`() {
        val fooFile = DiskEntry(
            name = "foo",
            type = DiskEntry.Type.File,
            path = Path.of("/dir/dir1/dir11/foo"),
            parent = DiskEntries.dir11,
            sizeOnDisk = 32
        )
        includeDiskEntry.invoke(fooFile) shouldBe false
        includeDiskEntry.invoke(fooFile, DiskEntries.rootDir) shouldBe false
    }
}
