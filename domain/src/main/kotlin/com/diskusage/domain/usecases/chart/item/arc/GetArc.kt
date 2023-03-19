package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.model.chart.Arc
import com.diskusage.domain.usecases.path.GetDepth
import com.diskusage.domain.usecases.path.GetRoot
import com.diskusage.libraries.ranges.until
import java.nio.file.Path

/**
 * Calculate the [Arc] for given `path` starting from `fromPath`
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
        path: Path,
        fromPath: Path = getRoot(path),
    ): Arc {
        val depth = getDepth(path, fromPath)

        val startAngle = getStartAngle(path, fromPath)
        val sweepAngle = getSweepAngle(path, fromPath)

        val startRadius = getStartRadius(depth)
        val arcWidth = getArcWidth(depth)

        return Arc(
            angleRange = startAngle until (startAngle + sweepAngle),
            radiusRange = startRadius until (startRadius + arcWidth),
        )
    }
}
