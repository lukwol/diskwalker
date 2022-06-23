package com.diskusage.domain.entities

import java.nio.file.Path

/**
 * Entry at [Path] that can either be [File] or [Directory]
 *
 * @property name Name of [DiskEntry]
 * @property path [Path] where [DiskEntry] is located
 * @property sizeOnDisk Calculated real space occupied on disk
 * @property parent [Directory] which contains [DiskEntry], if it's root parent is null
 */
sealed class DiskEntry {
    abstract val name: String
    abstract val path: Path
    abstract var sizeOnDisk: Long
    abstract val parent: Directory?

    /**
     * Relationship between two [disk entries][DiskEntry]
     */
    enum class Relationship {
        /**
         * Both [disk entries][DiskEntry] are the same [DiskEntry]
         */
        Identity,

        /**
         * When [DiskEntry] contains other [DiskEntry] it's an [Ancestor],
         * can have multiple intermediate [disk entries][DiskEntry]
         */
        Ancestor,

        /**
         * When [DiskEntry] is contained by other [DiskEntry] it's a [Descendant],
         * can have multiple intermediate [disk entries][DiskEntry]
         */
        Descendant,

        /**
         * Both [disk entries][DiskEntry] have the same [parent]
         */
        Sibling,

        /**
         * Both [disk entries][DiskEntry] are in [Unrelated] relationship,
         * when they are not in any other [Relationship]
         */
        Unrelated
    }

    /**
     * Represents [File]
     */
    class File(
        override val name: String,
        override val path: Path,
        override var sizeOnDisk: Long = 0,
        override val parent: Directory? = null,
    ) : DiskEntry()

    /**
     * Represents [Directory]
     *
     * @property children List of contained [disk entries][DiskEntry], might be empty
     */
    class Directory(
        override val name: String,
        override val path: Path,
        override var sizeOnDisk: Long = 0,
        override val parent: Directory? = null,
        var children: List<DiskEntry> = emptyList(),
    ) : DiskEntry()
}
