package com.diskusage.domain.usecases.path

import com.diskusage.domain.model.path.PathRelationship
import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Define [PathRelationship] between two paths
 */
class GetPathRelationship(
    private val getChildren: GetChildren,
) {
    operator fun invoke(path: Path, otherPath: Path) = when {
        path == otherPath -> PathRelationship.Identity
        otherPath in path.parent?.let(getChildren::invoke).orEmpty() -> PathRelationship.Sibling
        path.startsWith(otherPath) -> PathRelationship.Ancestor
        otherPath.startsWith(path) -> PathRelationship.Descendant
        else -> PathRelationship.Unrelated
    }
}
