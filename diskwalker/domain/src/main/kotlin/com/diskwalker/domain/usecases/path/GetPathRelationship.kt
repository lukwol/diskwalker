package com.diskwalker.domain.usecases.path

import com.diskwalker.domain.model.path.PathRelationship
import com.diskwalker.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Define [PathRelationship] between two paths
 */
class GetPathRelationship(
    private val getChildren: GetChildren,
) {
    operator fun invoke(path: Path, otherPath: Path, disk: Path) = when {
        path == otherPath -> PathRelationship.Identity
        path == disk -> PathRelationship.Disk
        otherPath in path.parent?.let(getChildren::invoke).orEmpty() -> PathRelationship.Sibling
        path.startsWith(otherPath) -> PathRelationship.Ancestor
        otherPath.startsWith(path) -> PathRelationship.Descendant
        else -> PathRelationship.Unrelated
    }
}
