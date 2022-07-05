package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry

/**
 * Define [Relationship][DiskEntry.Relationship] between two [disk entries][DiskEntry]
 *
 * Example relationship:
 * `otherDiskEntry` is [Ancestor][DiskEntry.Relationship.Ancestor] for `diskEntry`
 */
class GetRelationship {
    operator fun invoke(diskEntry: DiskEntry, otherDiskEntry: DiskEntry) = when {
        diskEntry.path == otherDiskEntry.path -> DiskEntry.Relationship.Identity
        otherDiskEntry.path in siblingsPaths(diskEntry) -> DiskEntry.Relationship.Sibling
        diskEntry.path.startsWith(otherDiskEntry.path) -> DiskEntry.Relationship.Ancestor
        otherDiskEntry.path.startsWith(diskEntry.path) -> DiskEntry.Relationship.Descendant
        else -> DiskEntry.Relationship.Unrelated
    }

    /**
     * All siblings for given [diskEntry] including given [diskEntry]
     */
    private fun siblingsPaths(diskEntry: DiskEntry) = (
            diskEntry.parent?.children
                ?.map(DiskEntry::path)
                ?: emptyList()
            )
}
