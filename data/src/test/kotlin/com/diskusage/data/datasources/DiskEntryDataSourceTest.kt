package com.diskusage.data.datasources

import com.diskusage.data.di.dataModule
import com.diskusage.domain.datasources.DiskEntryDataSource
import com.diskusage.support.FileSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

class DiskEntryDataSourceTest : KoinTest {

    private val diskEntryDataSource by inject<DiskEntryDataSource>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(dataModule)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @BeforeEach
    internal fun setUp() {
        declareMock<FileSize> {
            every { sizeOnDisk(any()) } returns 42
        }
    }

    @Test
    fun `example test`() {
        val path = mockk<Path>()
        every { path.isRegularFile() } returns true
        every { path.absolutePathString() } returns "/foo.txt"
        every { path.name } returns "foo.txt"

        val diskEntry = diskEntryDataSource.diskEntry(path)
        diskEntry.size shouldBe 42
    }
}
