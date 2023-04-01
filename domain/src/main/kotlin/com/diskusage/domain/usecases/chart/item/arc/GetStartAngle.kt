package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.model.chart.Arc
import com.diskusage.domain.model.path.PathRelationship
import com.diskusage.domain.usecases.path.GetParent
import com.diskusage.domain.usecases.path.GetPathRelationship
import com.diskusage.domain.usecases.path.GetSizeOnDisk
import com.diskusage.domain.usecases.path.SortPaths
import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Calculate start angle of the [Arc] based on given `path` starting from `fromPath`.
 *
 * To compute start angle it's necessary to first check the [relationship][PathRelationship]
 * between both [paths][Path].
 *
 * When both entries are [the same][PathRelationship.Identity]
 * or `fromPath` is [descendant][PathRelationship.Descendant] of `path` the result is **0f**.
 *
 * When `fromPath` is [unrelated][PathRelationship.Unrelated] or [sibling][PathRelationship.Sibling] to `path`,
 * the result is either **360f** or **0f** depending on the position of `path` towards `fromPath`.
 *
 * When `fromPath` is an [ancestor][PathRelationship.Ancestor] of `path`
 * the result is computing size offset of all preceding [paths][Path] and converting it into actual angle.
 *
 * @see calculateSizeOffset
 */
class GetStartAngle(
    private val getPathRelationship: GetPathRelationship,
    private val sortPaths: SortPaths,
    private val getChildren: GetChildren,
    private val sizeOnDisk: GetSizeOnDisk,
    private val getParent: GetParent,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path = fromPath,
    ): Float = when (getPathRelationship(path, fromPath, disk)) {
        PathRelationship.Identity, PathRelationship.Descendant, PathRelationship.Disk -> 0f
        PathRelationship.Unrelated, PathRelationship.Sibling -> {
            if (invoke(path, disk) > invoke(fromPath, disk)) 360f else 0f
        }

        PathRelationship.Ancestor -> {
            (calculateSizeOffset(path, fromPath, disk).toDouble() / sizeOnDisk(fromPath).toDouble())
                .takeIf(Double::isFinite)
                ?.times(360)
                ?.toFloat()
                ?: 0f
        }
    }

    /**
     * Calculate size offset by summing sizes for all larger siblings of [path]
     * and all larger siblings of its ancestors.
     *
     * Traverses by [parent][Path.getParent] until [fromPath] or root is reached.
     *
     * @see largerSiblingsSize
     */
    private fun calculateSizeOffset(
        path: Path,
        fromPath: Path,
        disk: Path,
        size: Long = 0,
    ): Long {
        val parent = getParent(path)
        return when {
            parent == null -> 0L
            path == fromPath -> 0L
            parent == fromPath -> size + largerSiblingsSize(path, disk)
            else -> calculateSizeOffset(parent, fromPath, disk, size + largerSiblingsSize(path, disk))
        }
    }

    /**
     * Sums all larger siblings sizes for given [Path]
     */
    private fun largerSiblingsSize(path: Path, disk: Path) =
        (getParent(path)?.let(getChildren::invoke).orEmpty())
            .let(sortPaths::invoke)
            .takeWhile { getPathRelationship(path, it, disk) != PathRelationship.Identity }
            .sumOf(sizeOnDisk::invoke)
}
