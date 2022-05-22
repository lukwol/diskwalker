package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetDepth(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = depth(diskEntry, fromDiskEntry)

    private fun depth(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, depth: Float = 1f): Float =
        when (getRelationship(diskEntry, fromDiskEntry)) {
            DiskEntry.Relationship.Identity -> depth
            DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Descendant -> 0f
            else -> depth(diskEntry.parent!!, fromDiskEntry, depth + 1)
        }
}
