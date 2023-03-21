package com.diskusage.domain.usecases.path

import com.diskusage.domain.model.path.PathRelationship
import java.nio.file.Path

/**
 * Calculates depth level, how much nested `path` is compared to `fromPath`
 */
class GetDepth(
    private val getPathRelationship: GetPathRelationship,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
    ) = depth(path, fromPath, disk)

    private fun depth(path: Path, fromPath: Path, disk: Path, depth: Int = 1): Int =
        when (getPathRelationship(path, fromPath, disk = disk)) {
            PathRelationship.Identity -> depth
            PathRelationship.Unrelated, PathRelationship.Descendant, PathRelationship.Disk -> 0
            PathRelationship.Ancestor, PathRelationship.Sibling -> depth(
                path = path.parent,
                fromPath = fromPath,
                disk = disk,
                depth = depth + 1,
            )
        }
}
