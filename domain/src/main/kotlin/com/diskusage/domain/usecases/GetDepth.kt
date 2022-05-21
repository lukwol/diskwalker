package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetDepth {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry.root,
    ) = depth(diskEntry, fromDiskEntry)

    private fun depth(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, depth: Float = 1f): Float =
        when (fromDiskEntry.relationship(diskEntry)) {
            DiskEntry.Relationship.Identity -> depth
            DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Ancestor -> 0f
            else -> depth(diskEntry.parent!!, fromDiskEntry, depth + 1)
        }
}
