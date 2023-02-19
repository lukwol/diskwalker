package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot
import com.diskusage.domain.usecases.diskentry.SortDiskEntries
import com.diskusage.domain.usecases.scan.GetChildren
import com.diskusage.domain.usecases.scan.GetSizeOnDisk
import java.nio.file.Path

/**
 * Calculate start angle of the [Arc] based on given `diskEntry` starting from `fromDiskEntry`.
 *
 * To compute start angle it's necessary to first check the [relationship][DiskEntry.Relationship]
 * between both [disk entries][DiskEntry].
 *
 * When both entries are [the same][DiskEntry.Relationship.Identity]
 * or `fromDiskEntry` is [descendant][DiskEntry.Relationship.Descendant] of `diskEntry` the result is **0f**.
 *
 * When `fromDiskEntry` is [unrelated][DiskEntry.Relationship.Unrelated] or [sibling][DiskEntry.Relationship.Sibling] to `diskEntry`,
 * the result is either **360f** or **0f** depending on the position of `diskEntry` towards `fromDiskEntry`.
 *
 * When `fromDiskEntry` is an [ancestor][DiskEntry.Relationship.Ancestor] of `diskEntry`
 * the result is computing size offset of all preceding [disk entries][DiskEntry] and converting it into actual angle.
 *
 * @see calculateSizeOffset
 */
class GetStartAngle(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
    private val sortDiskEntries: SortDiskEntries,
    private val getChildren: GetChildren,
    private val sizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ): Float = when (getRelationship(diskEntry, fromDiskEntry)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Descendant -> 0f
        DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Sibling -> {
            if (invoke(diskEntry) > invoke(fromDiskEntry)) 360f else 0f
        }

        DiskEntry.Relationship.Ancestor -> {
            (calculateSizeOffset(diskEntry, fromDiskEntry).toDouble() / fromDiskEntry.sizeOnDisk.toDouble())
                .takeIf(Double::isFinite)
                ?.times(360)
                ?.toFloat()
                ?: 0f
        }
    }

    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ): Float = when (getRelationship(path, fromPath)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Descendant -> 0f
        DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Sibling -> {
            if (invoke(path) > invoke(fromPath)) 360f else 0f
        }

        DiskEntry.Relationship.Ancestor -> {
            (calculateSizeOffset(path, fromPath).toDouble() / sizeOnDisk(fromPath).toDouble())
                .takeIf(Double::isFinite)
                ?.times(360)
                ?.toFloat()
                ?: 0f
        }
    }

    /**
     * Calculate size offset by summing sizes for all larger siblings of [diskEntry]
     * and all larger siblings of its ancestors.
     *
     * Traverses by [parent][DiskEntry.parent] until [fromDiskEntry] or root is reached.
     *
     * @see largerSiblingsSize
     */
    private fun calculateSizeOffset(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, size: Long = 0): Long = when {
        diskEntry.parent == null -> 0L
        diskEntry == fromDiskEntry -> 0L
        diskEntry.parent == fromDiskEntry -> size + largerSiblingsSize(diskEntry)
        else -> calculateSizeOffset(diskEntry.parent, fromDiskEntry, size + largerSiblingsSize(diskEntry))
    }

    private fun calculateSizeOffset(
        path: Path,
        fromPath: Path,
        size: Long = 0
    ): Long = when {
        path.parent == null -> 0L
        path == fromPath -> 0L
        path.parent == fromPath -> size + largerSiblingsSize(path)
        else -> calculateSizeOffset(path.parent, fromPath, size + largerSiblingsSize(path))
    }

    /**
     * Sums all larger siblings sizes for given [DiskEntry]
     */
    private fun largerSiblingsSize(diskEntry: DiskEntry) =
        (diskEntry.parent?.children.orEmpty())
            .let(sortDiskEntries::invoke)
            .takeWhile { getRelationship(diskEntry, it) != DiskEntry.Relationship.Identity }
            .sumOf(DiskEntry::sizeOnDisk)

    private fun largerSiblingsSize(path: Path) =
        (path.parent?.let(getChildren::invoke).orEmpty())
            .let(sortDiskEntries::invoke)
            .takeWhile { getRelationship(path, it) != DiskEntry.Relationship.Identity }
            .sumOf(sizeOnDisk::invoke)
}
