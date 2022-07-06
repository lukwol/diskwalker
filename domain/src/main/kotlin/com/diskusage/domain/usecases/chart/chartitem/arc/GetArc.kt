package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.core.until
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

/**
 * Calculate the [Arc] for given `diskEntry` starting from `fromDiskEntry`
 */
class GetArc(
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getStartRadius: GetStartRadius,
    private val getArcWidth: GetArcWidth,
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Arc {
        val depth = getDepth(diskEntry, fromDiskEntry)

        val startAngle = getStartAngle(diskEntry, fromDiskEntry)
        val sweepAngle = getSweepAngle(diskEntry, fromDiskEntry)

        val startRadius = getStartRadius(depth)
        val arcWidth = getArcWidth(depth)

        return Arc(
            angleRange = startAngle until (startAngle + sweepAngle),
            radiusRange = startRadius until (startRadius + arcWidth),
        )
    }
}
