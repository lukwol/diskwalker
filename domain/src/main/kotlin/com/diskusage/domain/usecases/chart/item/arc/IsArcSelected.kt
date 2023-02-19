package com.diskusage.domain.usecases.chart.item.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.chart.Arc
import kotlin.math.PI
import kotlin.math.atan2

/**
 * Check whether given `offset` is within `arc's` [angle][Arc.angleRange] and [radius][Arc.radiusRange] ranges.
 */
class IsArcSelected {
    operator fun invoke(
        arc: Arc,
        offset: Offset
    ): Boolean {
        val angle = offset.getAngle()
        val distance = offset.getDistance()
        return angle in arc.angleRange && distance in arc.radiusRange
    }
}

/**
 * Calculate [angle][Float] based from [Offset]
 */
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
