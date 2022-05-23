package com.diskusage.domain.entities

import java.nio.file.Path

sealed class DiskEntry {
    abstract val name: String
    abstract val path: Path
    abstract var size: Long
    abstract val parent: Directory?

    enum class Relationship {
        Identity, Ancestor, Descendant, Sibling, Unrelated
    }

    class File(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
    ) : DiskEntry()

    class Directory(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
        var children: List<DiskEntry> = listOf(),
    ) : DiskEntry()
}
