package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.Relationship
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
    operator fun invoke(path: Path, otherPath: Path) = when {
        path == otherPath -> Relationship.Identity
        otherPath in path.parent?.let(getChildren::invoke).orEmpty() -> Relationship.Sibling
        path.startsWith(otherPath) -> Relationship.Ancestor
        otherPath.startsWith(path) -> Relationship.Descendant
        else -> Relationship.Unrelated
    }
}
