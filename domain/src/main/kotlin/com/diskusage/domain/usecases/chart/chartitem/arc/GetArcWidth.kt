package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth

/**
 * Width of an [Arc][com.diskusage.domain.entities.Arc] that is drawn close to the center of the chart.
 */
private const val BigArcWidth = 100f

/**
 * Width of an [Arc][com.diskusage.domain.entities.Arc] that is drawn far from the center of the chart.
 */
private const val SmallArcWidth = 20f

/**
 * Calculates the width of an [Arc][com.diskusage.domain.entities.Arc] based on `itemDepth`
 */
class GetArcWidth {
    operator fun invoke(itemDepth: Int): Float {
        return when {
            itemDepth == 0 -> 0f
            itemDepth <= MaxBigArcsDepth -> BigArcWidth
            else -> SmallArcWidth
        }
    }
}
