package com.diskusage.domain.model

import java.nio.file.Path

/**
 * Entry at [Path] that can either be a [file][Type.File] or a [directory][Type.Directory]
 *
 * @property name Name of [DiskEntry]
 * @property path [Path] where [DiskEntry] is located
 * @property sizeOnDisk Calculated real space occupied on disk
 * @property parent [DiskEntry] which contains [DiskEntry], if it's root parent is null
 */
class DiskEntry(
    val name: String,
    val type: Type,
    val path: Path,
    val parent: DiskEntry?,
    var sizeOnDisk: Long = 0,
    var children: List<DiskEntry> = emptyList()
) {

    /**
     * Type of [DiskEntry], can either be a [File] or [Directory]
     */
    enum class Type {
        Directory, File
    }

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
}
