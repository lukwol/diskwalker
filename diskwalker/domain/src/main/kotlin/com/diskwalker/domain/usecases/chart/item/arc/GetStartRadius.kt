package com.diskwalker.domain.usecases.chart.item.arc

import com.diskwalker.domain.common.Constants.Chart.BigArcWidth
import com.diskwalker.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskwalker.domain.common.Constants.Chart.SmallArcWidth

/**
 * Calculates how far from the center of the chart, the arc should start
 */
class GetStartRadius {
    operator fun invoke(itemDepth: Int) = if (itemDepth <= MaxBigArcsDepth) {
        (itemDepth * BigArcWidth - BigArcWidth).coerceAtLeast(0f)
    } else {
        MaxBigArcsDepth * BigArcWidth + ((itemDepth - MaxBigArcsDepth) * SmallArcWidth - SmallArcWidth).coerceAtLeast(0f)
    }
}
