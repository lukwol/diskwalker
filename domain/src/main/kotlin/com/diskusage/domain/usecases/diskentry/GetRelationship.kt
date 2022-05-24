package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry

class GetRelationship {
    operator fun invoke(diskEntry: DiskEntry, otherDiskEntry: DiskEntry) = when {
        diskEntry.path == otherDiskEntry.path -> DiskEntry.Relationship.Identity
        otherDiskEntry.path in siblingsPaths(diskEntry) -> DiskEntry.Relationship.Sibling
        diskEntry.path.startsWith(otherDiskEntry.path) -> DiskEntry.Relationship.Ancestor
        otherDiskEntry.path.startsWith(diskEntry.path) -> DiskEntry.Relationship.Descendant
        else -> DiskEntry.Relationship.Unrelated
    }

    private fun siblingsPaths(diskEntry: DiskEntry) = (
        diskEntry.parent?.children
            ?.map(DiskEntry::path)
            ?: emptyList()
        )
}
