package com.diskusage.domain.stubs.stubs

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path

@Suppress("MemberVisibilityCanBePrivate")
object DiskEntryStubs {

    val rootFile = DiskEntry.File(
        name = "file",
        path = Path.of("/file"),
        parent = null,
        sizeOnDisk = 8192,
    )

    val rootDir = DiskEntry.Directory(
        name = "dir",
        path = Path.of("/dir"),
        parent = null,
        sizeOnDisk = 12800,
    )

    val file1 = DiskEntry.File(
        name = "file1",
        path = Path.of("/dir/file1"),
        parent = rootDir,
        sizeOnDisk = 5120,
    )

    val dir1 = DiskEntry.Directory(
        name = "dir1",
        path = Path.of("/dir/dir1"),
        parent = rootDir,
        sizeOnDisk = 3840,
    )

    val file11 = DiskEntry.File(
        name = "file11",
        path = Path.of("/dir/dir1/file11"),
        parent = dir1,
        sizeOnDisk = 1024,
    )

    val file12 = DiskEntry.File(
        name = "file12",
        path = Path.of("/dir/dir1/file12"),
        parent = dir1,
        sizeOnDisk = 2048,
    )

    val dir11 = DiskEntry.Directory(
        name = "dir11",
        path = Path.of("/dir/dir1/dir11"),
        parent = dir1,
        sizeOnDisk = 768,
    )

    val file111 = DiskEntry.File(
        name = "file111",
        path = Path.of("/dir/dir1/dir11/file111"),
        parent = dir11,
        sizeOnDisk = 512,
    )

    val file112 = DiskEntry.File(
        name = "file112",
        path = Path.of("/dir/dir1/dir11/file112"),
        parent = dir11,
        sizeOnDisk = 256,
    )

    val dir2 = DiskEntry.Directory(
        name = "dir2",
        path = Path.of("/dir/dir2"),
        parent = rootDir,
        sizeOnDisk = 3840,
    )

    val file21 = DiskEntry.File(
        name = "file21",
        path = Path.of("/dir/dir2/file21"),
        parent = dir2,
        sizeOnDisk = 2304,
    )

    val file22 = DiskEntry.File(
        name = "file22",
        path = Path.of("/dir/dir2/file22"),
        parent = dir2,
        sizeOnDisk = 1536,
    )

    init {
        dir11.children = listOf(file111, file112)
        dir1.children = listOf(file11, file12, dir11)
        dir2.children = listOf(file21, file22)
        rootDir.children = listOf(file1, dir1, dir2)
    }
}
