package com.diskusage.domain.usecases.chart.chartitem.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc
import kotlin.math.PI
import kotlin.math.atan2

class IsArcSelected {
    operator fun invoke(
        arc: Arc,
        offset: Offset,
    ): Boolean {
        val distance = offset.getDistance()
        val angle = offset.getAngle()
        return angle >= arc.startAngle &&
            angle < arc.startAngle + arc.sweepAngle &&
            distance >= arc.depth * Constants.ArcWidth - Constants.ArcWidth &&
            distance < arc.depth * Constants.ArcWidth
    }
}

private fun Offset.getAngle(): Float {
    var atan2 = 0 * PI - atan2(x, y)
    if (atan2 < 0) {
        atan2 += 2 * PI
    }
    var degrees = (atan2 * (180 / PI))
    degrees += 90
    if (degrees > 360) {
        degrees -= 360
    }
    return degrees.toFloat()
}
