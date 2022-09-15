package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot

/**
 * Calculate sweep angle of the [Arc] based on given `diskEntry` starting from `fromDiskEntry`.
 *
 * To compute sweep angle it's necessary to first check the [relationship][DiskEntry.Relationship]
 * between both [disk entries][DiskEntry].
 *
 * When both entries are [the same][DiskEntry.Relationship.Identity]
 * or `fromDiskEntry` is [descendant][DiskEntry.Relationship.Descendant] of `diskEntry` the result is **360f**.
 *
 * When `fromDiskEntry` is [unrelated][DiskEntry.Relationship.Unrelated]
 * or [sibling][DiskEntry.Relationship.Sibling] to `diskEntry`, the result is **0f**.
 *
 * When `fromDiskEntry` is an [ancestor][DiskEntry.Relationship.Ancestor] of `diskEntry`
 * the result is dividing `diskEntry` size by `fromDiskEntry` size and converting it into actual angle.
 */
class GetSweepAngle(
    private val getRoot: GetRoot,
    private val getRelationship: GetRelationship
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
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
