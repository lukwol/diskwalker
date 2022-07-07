package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants.Chart.BigArcWidth
import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.SmallArcWidth

/**
 * Calculates how far from the center of the chart, the arc should start
 */
class GetStartRadius {
    operator fun invoke(itemDepth: Int): Float {
        return if (itemDepth <= MaxBigArcsDepth) {
            (itemDepth * BigArcWidth - BigArcWidth).coerceAtLeast(0f)
        } else {
            MaxBigArcsDepth * BigArcWidth + ((itemDepth - MaxBigArcsDepth) * SmallArcWidth - SmallArcWidth).coerceAtLeast(0f)
        }
    }
}
