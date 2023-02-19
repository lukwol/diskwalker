package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.Relationship
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot
import com.diskusage.domain.usecases.scan.GetSizeOnDisk
import java.nio.file.Path

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
    private val getRelationship: GetRelationship,
    private val getSizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ) = when (getRelationship(path, fromPath)) {
        Relationship.Identity, Relationship.Descendant -> 360f
        Relationship.Unrelated, Relationship.Sibling -> 0f
        Relationship.Ancestor -> (getSizeOnDisk(path).toDouble() / getSizeOnDisk(fromPath).toDouble())
            .takeIf(Double::isFinite)
            ?.times(360)
            ?.toFloat()
            ?: 0f
    }
}
