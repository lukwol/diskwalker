package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.nio.file.Path

class SortTestDiskEntriesTest : KoinTest {

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
                TestDiskEntries.file22,
                TestDiskEntries.dir1,
                TestDiskEntries.file11,
                TestDiskEntries.file111,
                TestDiskEntries.file112,
                TestDiskEntries.dir11,
                TestDiskEntries.rootDir,
                TestDiskEntries.file12,
                TestDiskEntries.rootFile,
                TestDiskEntries.file21,
                TestDiskEntries.file1,
                TestDiskEntries.dir2
            )
        ) shouldBe listOf(
            TestDiskEntries.rootDir,
            TestDiskEntries.rootFile,
            TestDiskEntries.file1,
            TestDiskEntries.dir1,
            TestDiskEntries.dir2,
            TestDiskEntries.file21,
            TestDiskEntries.file12,
            TestDiskEntries.file22,
            TestDiskEntries.file11,
            TestDiskEntries.dir11,
            TestDiskEntries.file111,
            TestDiskEntries.file112
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
