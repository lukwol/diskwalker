package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry

class GetDepth(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
) {

    /**
     * Calculates depth level, how much nested [diskEntry] is compared to [fromDiskEntry]
     *
     * @param diskEntry [DiskEntry] for which the depth level will be calculated
     * @param fromDiskEntry [DiskEntry] from which calculation will start, uses root if not given
     * @return Computed [diskEntry] depth in relation to [fromDiskEntry]
     * @see com.diskusage.domain.common.Constants.MaxArcsDepth
     */
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = depth(diskEntry, fromDiskEntry)

    private fun depth(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, depth: Int = 1): Int =
        when (getRelationship(diskEntry, fromDiskEntry)) {
            DiskEntry.Relationship.Identity -> depth
            DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Descendant -> 0
            DiskEntry.Relationship.Ancestor, DiskEntry.Relationship.Sibling -> {
                depth(diskEntry.parent!!, fromDiskEntry, depth + 1)
            }
        }
}
