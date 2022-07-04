package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetArc(
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
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
        var startRadius = depth.coerceAtMost(Constants.MaxBigArcsDepth) * Constants.BigArcWidth - Constants.BigArcWidth
        if (depth > Constants.MaxBigArcsDepth) {
            startRadius += (depth - Constants.MaxBigArcsDepth) * Constants.SmallArcWidth - Constants.SmallArcWidth + Constants.BigArcWidth
        }
        startRadius = startRadius.coerceAtLeast(0f)

        val endRadius = when {
            depth == 0 -> 0f
            depth <= Constants.MaxBigArcsDepth -> {
                startRadius + Constants.BigArcWidth
            }
            else -> {
                startRadius + Constants.SmallArcWidth
            }
        }

        return Arc(
            angleRange = startAngle until (startAngle + sweepAngle),
            radiusRange = startRadius until endRadius,
        )
    }
}

data class OpenFloatRange(val start: Float, val end: Float)

infix fun Float.until(to: Float) = OpenFloatRange(this, to)
operator fun OpenFloatRange.contains(f: Float) = start < f && f < end
