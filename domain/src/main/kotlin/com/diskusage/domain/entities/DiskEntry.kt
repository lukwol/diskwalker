package com.diskusage.domain.entities

import java.nio.file.Path

sealed interface DiskEntry {
    val name: String
    val path: Path
    var size: Long
    val parent: Directory?
    var hasSizeCalculated: Boolean

    class File(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
        override var hasSizeCalculated: Boolean = false,
    ) : DiskEntry

    class Directory(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
        override var hasSizeCalculated: Boolean = false,
        var children: List<DiskEntry> = listOf(),
    ) : DiskEntry
}
