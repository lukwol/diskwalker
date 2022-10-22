package com.diskusage.domain.stubs

import com.diskusage.domain.model.DiskEntry
import java.nio.file.Path

object DiskEntries {

    val rootFile = DiskEntry(
        name = "file",
        type = DiskEntry.Type.File,
        path = Path.of("/file"),
        parent = null,
        sizeOnDisk = 8192
    )

    val rootDir = DiskEntry(
        name = "dir",
        type = DiskEntry.Type.Directory,
        path = Path.of("/dir"),
        parent = null,
        sizeOnDisk = 12800
    )

    val file1 = DiskEntry(
        name = "file1",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/file1"),
        parent = rootDir,
        sizeOnDisk = 5120
    )

    val dir1 = DiskEntry(
        name = "dir1",
        type = DiskEntry.Type.Directory,
        path = Path.of("/dir/dir1"),
        parent = rootDir,
        sizeOnDisk = 3840
    )

    val file11 = DiskEntry(
        name = "file11",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir1/file11"),
        parent = dir1,
        sizeOnDisk = 1024
    )

    val file12 = DiskEntry(
        name = "file12",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir1/file12"),
        parent = dir1,
        sizeOnDisk = 2048
    )

    val dir11 = DiskEntry(
        name = "dir11",
        type = DiskEntry.Type.Directory,
        path = Path.of("/dir/dir1/dir11"),
        parent = dir1,
        sizeOnDisk = 768
    )

    val file111 = DiskEntry(
        name = "file111",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir1/dir11/file111"),
        parent = dir11,
        sizeOnDisk = 512
    )

    val file112 = DiskEntry(
        name = "file112",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir1/dir11/file112"),
        parent = dir11,
        sizeOnDisk = 256
    )

    val dir2 = DiskEntry(
        name = "dir2",
        type = DiskEntry.Type.Directory,
        path = Path.of("/dir/dir2"),
        parent = rootDir,
        sizeOnDisk = 3840
    )

    val file21 = DiskEntry(
        name = "file21",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir2/file21"),
        parent = dir2,
        sizeOnDisk = 2304
    )

    val file22 = DiskEntry(
        name = "file22",
        type = DiskEntry.Type.File,
        path = Path.of("/dir/dir2/file22"),
        parent = dir2,
        sizeOnDisk = 1536
    )

    init {
        dir11.children = listOf(file111, file112)
        dir1.children = listOf(file11, file12, dir11)
        dir2.children = listOf(file21, file22)
        rootDir.children = listOf(file1, dir1, dir2)
    }
}
