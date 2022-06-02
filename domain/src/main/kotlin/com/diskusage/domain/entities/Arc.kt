package com.diskusage.domain.entities

/**
 * An arc specification that will be used for drawing.
 *
 * @property startAngle Starting angle in degrees. 0 represents 3 o'clock
 * @property sweepAngle Size of the arc in degrees that is drawn clockwise relative to [startAngle]
 * @property depth determines how far from center [Arc] is drawn
 *
 * @see androidx.compose.ui.graphics.drawscope.DrawScope.drawArc
 */
data class Arc(
    val startAngle: Float,
    val sweepAngle: Float,
    val depth: Float,
)
