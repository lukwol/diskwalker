package com.diskusage.domain.usecases.path

import com.diskusage.domain.model.path.PathRelationship
import java.nio.file.Path

/**
 * Calculates depth level, how much nested `path` is compared to `fromPath`
 */
class GetDepth(
    private val getRoot: GetRoot,
    private val getPathRelationship: GetPathRelationship,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path),
    ) = depth(path, fromPath)

    private fun depth(path: Path, fromPath: Path, depth: Int = 1): Int =
        when (getPathRelationship(path, fromPath)) {
            PathRelationship.Identity -> depth
            PathRelationship.Unrelated, PathRelationship.Descendant -> 0
            PathRelationship.Ancestor, PathRelationship.Sibling -> depth(
                path = path.parent,
                fromPath = fromPath,
                depth = depth + 1,
            )
        }
}
