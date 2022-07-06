package com.diskusage.domain.usecases.chart

import com.diskusage.domain.di.DomainModule
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.stubs.DiskEntryStubs
import io.kotest.matchers.shouldBe
import io.mockk.mockkClass
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import java.nio.file.Path

class SortDiskEntriesTest : KoinTest {

    private val sortDiskEntries by inject<SortDiskEntries>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(DomainModule.module)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @Test
    fun `items should be sorted with size ascending then by name descending`() {
        sortDiskEntries(
            listOf(
                DiskEntryStubs.file22,
                DiskEntryStubs.dir1,
                DiskEntryStubs.file11,
                DiskEntryStubs.file111,
                DiskEntryStubs.file112,
                DiskEntryStubs.dir11,
                DiskEntryStubs.rootDir,
                DiskEntryStubs.file12,
                DiskEntryStubs.rootFile,
                DiskEntryStubs.file21,
                DiskEntryStubs.file1,
                DiskEntryStubs.dir2,
            )
        ) shouldBe listOf(
            DiskEntryStubs.rootDir,
            DiskEntryStubs.rootFile,
            DiskEntryStubs.file1,
            DiskEntryStubs.dir1,
            DiskEntryStubs.dir2,
            DiskEntryStubs.file21,
            DiskEntryStubs.file12,
            DiskEntryStubs.file22,
            DiskEntryStubs.file11,
            DiskEntryStubs.dir11,
            DiskEntryStubs.file111,
            DiskEntryStubs.file112,
        )
    }

    @Test
    fun `more items with the same size should be sorted by name descending`() {
        val barFile = DiskEntry.File(
            name = "bar",
            path = Path.of("/bar"),
            parent = null,
            sizeOnDisk = 1024,
        )
        val fooFile = DiskEntry.File(
            name = "foo",
            path = Path.of("/foo"),
            parent = null,
            sizeOnDisk = 1024,
        )
        val bazFile = DiskEntry.File(
            name = "baz",
            path = Path.of("/baz"),
            parent = null,
            sizeOnDisk = 1024,
        )
        sortDiskEntries(
            listOf(barFile, fooFile, bazFile)
        ) shouldBe listOf(barFile, bazFile, fooFile)
    }
}
