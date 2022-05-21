package com.diskusage.domain.entities

import java.nio.file.Path
import kotlin.io.path.absolutePathString

sealed class DiskEntry {
    abstract val name: String
    abstract val path: Path
    abstract var size: Long
    abstract val parent: Directory?
    abstract var hasSizeCalculated: Boolean

    enum class Relationship {
        Identity, Ancestor, Descendant, Sibling, Unrelated
    }

    class File(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
        override var hasSizeCalculated: Boolean = false,
    ) : DiskEntry()

    class Directory(
        override val name: String,
        override val path: Path,
        override var size: Long = 0,
        override val parent: Directory? = null,
        override var hasSizeCalculated: Boolean = false,
        var children: List<DiskEntry> = listOf(),
    ) : DiskEntry()

    val root: DiskEntry get() = parent?.root ?: this

    val siblings: List<DiskEntry>
        get() = parent
            ?.children
            ?.filterNot { it.path.absolutePathString() == path.absolutePathString() }
            ?: emptyList()

    fun relationship(other: DiskEntry): Relationship {
        return when {
            path.absolutePathString() == other.path.absolutePathString() -> Relationship.Identity
            other.path.absolutePathString() in siblings.map { it.path.absolutePathString() } -> Relationship.Sibling
            path.absolutePathString().startsWith(other.path.absolutePathString()) -> Relationship.Ancestor
            other.path.absolutePathString().startsWith(path.absolutePathString()) -> Relationship.Descendant
            else -> Relationship.Unrelated
        }
    }
}
