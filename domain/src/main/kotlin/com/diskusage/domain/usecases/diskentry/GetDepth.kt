package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.Relationship
import java.nio.file.Path

/**
 * Calculates depth level, how much nested `diskEntry` is compared to `fromDiskEntry`
 */
class GetDepth(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship
) {
    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ) = depth(path, fromPath)

    private fun depth(path: Path, fromPath: Path, depth: Int = 1): Int =
        when (getRelationship(path, fromPath)) {
            Relationship.Identity -> depth
            Relationship.Unrelated, Relationship.Descendant -> 0
            Relationship.Ancestor, Relationship.Sibling -> depth(
                path = path.parent,
                fromPath = fromPath,
                depth = depth + 1
            )
        }
}
