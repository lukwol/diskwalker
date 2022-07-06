package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry
import org.koin.core.annotation.Single

/**
 * Calculates depth level, how much nested `diskEntry` is compared to `fromDiskEntry`
 */
@Single
class GetDepth(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
) {
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
