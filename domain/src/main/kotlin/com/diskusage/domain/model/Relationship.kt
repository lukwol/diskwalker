package com.diskusage.domain.model

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
