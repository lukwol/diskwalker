package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetSweepAngle(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = when (getRelationship(diskEntry, fromDiskEntry)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Descendant -> 360f
        DiskEntry.Relationship.Unrelated, DiskEntry.Relationship.Sibling -> 0f
        DiskEntry.Relationship.Ancestor -> (diskEntry.sizeOnDisk.toDouble() / fromDiskEntry.sizeOnDisk.toDouble())
            .takeIf(Double::isFinite)
            ?.times(360)
            ?.toFloat()
            ?: 0f
    }
}
