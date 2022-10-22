package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.stubs.DiskEntries
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.nio.file.Path

class SortDiskEntriesTest : KoinTest {

    private val sortDiskEntries by inject<SortDiskEntries>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `items should be sorted with size ascending then by name descending`() {
        sortDiskEntries(
            listOf(
                DiskEntries.file22,
                DiskEntries.dir1,
                DiskEntries.file11,
                DiskEntries.file111,
                DiskEntries.file112,
                DiskEntries.dir11,
                DiskEntries.rootDir,
                DiskEntries.file12,
                DiskEntries.rootFile,
                DiskEntries.file21,
                DiskEntries.file1,
                DiskEntries.dir2
            )
        ) shouldBe listOf(
            DiskEntries.rootDir,
            DiskEntries.rootFile,
            DiskEntries.file1,
            DiskEntries.dir1,
            DiskEntries.dir2,
            DiskEntries.file21,
            DiskEntries.file12,
            DiskEntries.file22,
            DiskEntries.file11,
            DiskEntries.dir11,
            DiskEntries.file111,
            DiskEntries.file112
        )
    }

    @Test
    fun `more items with the same size should be sorted by name descending`() {
        val barFile = DiskEntry(
            name = "bar",
            type = DiskEntry.Type.File,
            path = Path.of("/bar"),
            parent = null,
            sizeOnDisk = 1024
        )
        val fooFile = DiskEntry(
            name = "foo",
            type = DiskEntry.Type.File,
            path = Path.of("/foo"),
            parent = null,
            sizeOnDisk = 1024
        )
        val bazFile = DiskEntry(
            name = "baz",
            type = DiskEntry.Type.File,
            path = Path.of("/baz"),
            parent = null,
            sizeOnDisk = 1024
        )
        sortDiskEntries(
            listOf(barFile, fooFile, bazFile)
        ) shouldBe listOf(barFile, bazFile, fooFile)
    }
}
