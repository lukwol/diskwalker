package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry
import java.nio.file.Path

/**
 * Calculates depth level, how much nested `diskEntry` is compared to `fromDiskEntry`
 */
class GetDepth(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ) = depth(diskEntry, fromDiskEntry)

    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ) = depth(path, fromPath)

    private fun depth(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, depth: Int = 1): Int =
        when (getRelationship(diskEntry, fromDiskEntry)) {
            DiskEntry.Relationship.Identity -> depth
            DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Descendant -> 0
            DiskEntry.Relationship.Ancestor, DiskEntry.Relationship.Sibling -> {
                depth(diskEntry.parent!!, fromDiskEntry, depth + 1)
            }
        }

    private fun depth(path: Path, fromPath: Path, depth: Int = 1): Int =
        when (getRelationship(path, fromPath)) {
            DiskEntry.Relationship.Identity -> depth
            DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Descendant -> 0
            DiskEntry.Relationship.Ancestor, DiskEntry.Relationship.Sibling -> depth(
                path = path.parent,
                fromPath = fromPath,
                depth = depth + 1
            )
        }
}
