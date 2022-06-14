package com.diskusage.domain.usecases.chart.chartitem.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.entities.Arc
import kotlin.math.PI
import kotlin.math.atan2

class IsArcSelected {
    operator fun invoke(
        arc: Arc,
        offset: Offset,
    ): Boolean {
        val angle = offset.getAngle()
        val distance = offset.getDistance()
        return angle >= arc.angleRange.start &&
            angle < arc.angleRange.endInclusive &&
            distance >= arc.radiusRange.start &&
            distance < arc.radiusRange.endInclusive
    }
}

private fun Offset.getAngle(): Float {
    var atan2 = 0 * PI - atan2(x, y)
    if (atan2 < 0) {
        atan2 += 2 * PI
    }
    var degrees = (atan2 * (180 / PI)).toFloat()
    degrees += 90
    degrees %= 360
    return degrees
}
