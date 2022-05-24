package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetStartAngle(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
    private val sortDiskEntries: SortDiskEntries,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Float = when (getRelationship(diskEntry, fromDiskEntry)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Descendant -> 0f
        DiskEntry.Relationship.Ancestor ->
            (calculateSize(diskEntry, fromDiskEntry).toDouble() / fromDiskEntry.size.toDouble())
                .takeIf(Double::isFinite)
                ?.times(360)
                ?.toFloat()
                ?: 0f
        else -> if (invoke(diskEntry) > invoke(fromDiskEntry)) 360f else 0f
    }

    private fun calculateSize(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, size: Long = 0): Long = when {
        diskEntry.parent == null -> 0L
        diskEntry == fromDiskEntry -> 0L
        diskEntry.parent == fromDiskEntry -> size + largerSiblingsSize(diskEntry)
        else -> calculateSize(diskEntry.parent!!, fromDiskEntry, size + largerSiblingsSize(diskEntry))
    }

    private fun largerSiblingsSize(diskEntry: DiskEntry) =
        (diskEntry.parent?.children ?: emptyList())
            .let(sortDiskEntries::invoke)
            .takeLastWhile { getRelationship(diskEntry, it) != DiskEntry.Relationship.Identity }
            .sumOf(DiskEntry::size)
}
