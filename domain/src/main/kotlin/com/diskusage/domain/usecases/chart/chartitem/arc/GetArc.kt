package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.until
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetArc(
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getStartRadius: GetStartRadius,
    private val getArcWidth: GetArcWidth,
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    /**
     * Calculate the [Arc] for given [diskEntry] starting from [fromDiskEntry]
     *
     * @param diskEntry [DiskEntry] for which the [Arc] is calculated
     * @param fromDiskEntry [DiskEntry] from which calculation will start, uses root if not given
     * @return [Arc] with calculated [angle][Arc.angleRange] and [radius][Arc.radiusRange] [ranges][ClosedFloatingPointRange]
     */
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Arc {
        val startAngle = getStartAngle(diskEntry, fromDiskEntry)
        val sweepAngle = getSweepAngle(diskEntry, fromDiskEntry)
        val depth = getDepth(diskEntry, fromDiskEntry)
        val startRadius = getStartRadius(depth)
        val arcWidth = getArcWidth(depth)

        return Arc(
            angleRange = startAngle until (startAngle + sweepAngle),
            radiusRange = startRadius until (startRadius + arcWidth),
        )
    }
}
