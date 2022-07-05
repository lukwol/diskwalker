package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth

/**
 * Width of an [Arc][com.diskusage.domain.entities.Arc] that are drawn for each item close to the center of the chart.
 */
private const val BigArcWidth = 100f

/**
 * Width of an [Arc][com.diskusage.domain.entities.Arc] that are drawn for each item far from the center of the chart.
 */
private const val SmallArcWidth = 20f

class GetArcWidth {
    operator fun invoke(itemDepth: Int): Float {
        return when {
            itemDepth == 0 -> 0f
            itemDepth <= MaxBigArcsDepth -> BigArcWidth
            else -> SmallArcWidth
        }
    }
}
