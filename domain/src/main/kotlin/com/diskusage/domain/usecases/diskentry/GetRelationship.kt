package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry

class GetRelationship {

    /**
     * Define [Relationship][DiskEntry.Relationship] between two [disk entries][DiskEntry]
     *
     * Example relationship:
     * [otherDiskEntry] is [Ancestor][DiskEntry.Relationship.Ancestor] for [diskEntry]
     *
     * @param diskEntry [DiskEntry] for which relationship will be defined
     * @param otherDiskEntry [DiskEntry] which will be compared to given [diskEntry]
     * @return found [relationship][DiskEntry.Relationship] between given [diskEntry] and [otherDiskEntry]
     */
    operator fun invoke(diskEntry: DiskEntry, otherDiskEntry: DiskEntry) = when {
        diskEntry.path == otherDiskEntry.path -> DiskEntry.Relationship.Identity
        otherDiskEntry.path in siblingsPaths(diskEntry) -> DiskEntry.Relationship.Sibling
        diskEntry.path.startsWith(otherDiskEntry.path) -> DiskEntry.Relationship.Ancestor
        otherDiskEntry.path.startsWith(diskEntry.path) -> DiskEntry.Relationship.Descendant
        else -> DiskEntry.Relationship.Unrelated
    }

    /**
     * All siblings for given [diskEntry] including given [diskEntry]
     *
     * @param diskEntry for which siblings will be looked for
     * @return all [diskEntry's][diskEntry] parent's [children][DiskEntry.Directory.children] or empty list if there are none
     */
    private fun siblingsPaths(diskEntry: DiskEntry) = (
        diskEntry.parent?.children
            ?.map(DiskEntry::path)
            ?: emptyList()
        )
}
