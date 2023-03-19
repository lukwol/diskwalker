package com.diskusage.domain.model.chart

import com.diskusage.libraries.ranges.HalfOpenFloatRange

/**
 * An arc specification that will be used for drawing.
 *
 * @property angleRange Angle rang in degrees 0 represents 3 o'clock
 * @property radiusRange Range how far from center [Arc] is drawn
 * @property sweepAngle Difference between end angle and start angle
 * @property width Difference between end radius and start radius
 *
 * @see androidx.compose.ui.graphics.drawscope.DrawScope.drawArc
 */
data class Arc(
    val angleRange: HalfOpenFloatRange,
    val radiusRange: HalfOpenFloatRange,
) {
    val sweepAngle: Float = angleRange.end - angleRange.start
    val width: Float = radiusRange.end - radiusRange.start
}
