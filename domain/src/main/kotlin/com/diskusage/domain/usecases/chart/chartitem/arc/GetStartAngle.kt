package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetStartAngle(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
    private val sortDiskEntries: SortDiskEntries,
) {
    /**
     * Calculate start angle for the [Arc] based on [DiskEntry] starting from its root or specific [DiskEntry].
     *
     * To compute actual start angle it's necessary to first check the [Relationship][DiskEntry.Relationship].
     * between both [disk entries][DiskEntry].
     *
     * When both entries are the [same][DiskEntry.Relationship.Identity] [DiskEntry]
     * or [fromDiskEntry] is [descendant][DiskEntry.Relationship.Ancestor] the result is **0f**.
     *
     * When [fromDiskEntry] is [unrelated][DiskEntry.Relationship.Unrelated] or [sibling][DiskEntry.Relationship.Sibling],
     * the result is either **360f** or **0f** depending on the position of [diskEntry] in relation to [fromDiskEntry].
     *
     * When [fromDiskEntry] is correctly an [ancestor][DiskEntry.Relationship.Ancestor] of [diskEntry]
     * it's measured by calculating size offset of all preceding [disk entries][DiskEntry].
     *
     * @param diskEntry [DiskEntry] for which the start angle is calculated
     * @param fromDiskEntry [DiskEntry] from which calculation will start, uses root if not given
     * @return [Arc] with calculated start angle
     */
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
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

    /**
     * Calculate size offset by summing all larger siblings of [diskEntry]
     * and all larger siblings of all it's ancestors traversing through it's [parents][DiskEntry.parent] recursively,
     * until it reaches [fromDiskEntry] or root (given [diskEntry] has no parent).
     */
    private fun calculateSizeOffset(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, size: Long = 0): Long = when {
        diskEntry.parent == null -> 0L
        diskEntry == fromDiskEntry -> 0L
        diskEntry.parent == fromDiskEntry -> size + largerSiblingsSize(diskEntry)
        else -> calculateSizeOffset(diskEntry.parent!!, fromDiskEntry, size + largerSiblingsSize(diskEntry))
    }

    /**
     * Sums all larger siblings sizes for given [DiskEntry]
     */
    private fun largerSiblingsSize(diskEntry: DiskEntry) =
        (diskEntry.parent?.children ?: emptyList())
            .let(sortDiskEntries::invoke)
            .takeWhile { getRelationship(diskEntry, it) != DiskEntry.Relationship.Identity }
            .sumOf(DiskEntry::sizeOnDisk)
}
