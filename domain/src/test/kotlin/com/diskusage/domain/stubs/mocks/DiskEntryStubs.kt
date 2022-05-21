package com.diskusage.domain.stubs.mocks

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path

@Suppress("MemberVisibilityCanBePrivate")
object DiskEntryStubs {

    val rootFile = DiskEntry.File(
        name = "file",
        path = Path.of("/file"),
        parent = null,
        size = 42,
        hasSizeCalculated = true,
    )

    val rootDir = DiskEntry.Directory(
        name = "dir",
        path = Path.of("/dir"),
        parent = null,
        hasSizeCalculated = true,
    )

    val file1 = DiskEntry.File(
        name = "file1",
        path = Path.of("/dir/file1"),
        parent = rootDir,
        size = 1024,
        hasSizeCalculated = true,
    )

    val dir1 = DiskEntry.Directory(
        name = "dir1",
        path = Path.of("/dir/dir1"),
        parent = rootDir,
        hasSizeCalculated = true,
    )

    val file11 = DiskEntry.File(
        name = "file11",
        path = Path.of("/dir/dir1/file11"),
        parent = dir1,
        size = 1024,
        hasSizeCalculated = true,
    )

    val file12 = DiskEntry.File(
        name = "file12",
        path = Path.of("/dir/dir1/file12"),
        parent = dir1,
        size = 2048,
        hasSizeCalculated = true,
    )

    val dir11 = DiskEntry.Directory(
        name = "dir11",
        path = Path.of("/dir/dir1/dir11"),
        parent = dir1,
        hasSizeCalculated = true,
    )

    val file111 = DiskEntry.File(
        name = "file111",
        path = Path.of("/dir/dir1/dir11/file111"),
        parent = dir11,
        size = 512,
        hasSizeCalculated = true,
    )

    val file112 = DiskEntry.File(
        name = "file112",
        path = Path.of("/dir/dir1/dir11/file112"),
        parent = dir11,
        size = 256,
        hasSizeCalculated = true,
    )

    val dir2 = DiskEntry.Directory(
        name = "dir2",
        path = Path.of("/dir/dir2"),
        parent = rootDir,
        hasSizeCalculated = true,
    )

    val file21 = DiskEntry.File(
        name = "file21",
        path = Path.of("/dir/dir2/file21"),
        parent = dir2,
        size = 2048,
        hasSizeCalculated = true,
    )

    val file22 = DiskEntry.File(
        name = "file22",
        path = Path.of("/dir/dir2/file22"),
        parent = dir2,
        size = 1024,
        hasSizeCalculated = true,
    )

    init {
        listOf(file111, file112).let {
            dir11.size = it.sumOf(DiskEntry::size)
            dir11.children = it
        }

        listOf(file11, file12, dir11).let {
            dir1.size = it.sumOf(DiskEntry::size)
            dir1.children = it
        }

        listOf(file21, file22).let {
            dir2.size = it.sumOf(DiskEntry::size)
            dir2.children = it
        }

        listOf(file1, dir1, dir2).let {
            rootDir.size = it.sumOf(DiskEntry::size)
            rootDir.children = it
        }
    }

}
