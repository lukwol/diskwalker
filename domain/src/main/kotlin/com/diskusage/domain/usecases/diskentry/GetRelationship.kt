package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Define [Relationship][DiskEntry.Relationship] between two [disk entries][DiskEntry]
 *
 * Example relationship:
 * `otherDiskEntry` is [Ancestor][DiskEntry.Relationship.Ancestor] for `diskEntry`
 */
class GetRelationship(
    private val getChildren: GetChildren
) {
    operator fun invoke(diskEntry: DiskEntry, otherDiskEntry: DiskEntry) = when {
        diskEntry.path == otherDiskEntry.path -> DiskEntry.Relationship.Identity
        otherDiskEntry.path in siblingsPaths(diskEntry) -> DiskEntry.Relationship.Sibling
        diskEntry.path.startsWith(otherDiskEntry.path) -> DiskEntry.Relationship.Ancestor
        otherDiskEntry.path.startsWith(diskEntry.path) -> DiskEntry.Relationship.Descendant
        else -> DiskEntry.Relationship.Unrelated
    }

    operator fun invoke(path: Path, otherPath: Path) = when {
        path == otherPath -> DiskEntry.Relationship.Identity
        otherPath in path.parent?.let(getChildren::invoke).orEmpty() -> DiskEntry.Relationship.Sibling
        path.startsWith(otherPath) -> DiskEntry.Relationship.Ancestor
        otherPath.startsWith(path) -> DiskEntry.Relationship.Descendant
        else -> DiskEntry.Relationship.Unrelated
    }

    /**
     * All siblings for given [diskEntry] including given [diskEntry]
     */
    private fun siblingsPaths(diskEntry: DiskEntry) = (
        diskEntry.parent?.children
            ?.map(DiskEntry::path)
            .orEmpty()
        )
}
