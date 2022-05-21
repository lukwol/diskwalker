package com.diskusage.domain.stubs.mocks

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path

object DiskEntryStub {
    val file = DiskEntry.File(
        name = "foo",
        path = Path.of("foo.txt"),
        size = 42,
        parent = null,
        hasSizeCalculated = true
    )

    val dir = DiskEntry.Directory(
        name = "dir",
        path = Path.of("/dir"),
        size = 256,
        parent = null,
        hasSizeCalculated = true,
        children = listOf(file)
    )
}
