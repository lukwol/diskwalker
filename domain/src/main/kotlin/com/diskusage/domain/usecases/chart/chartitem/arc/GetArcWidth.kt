package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants.Chart.BigArcWidth
import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.SmallArcWidth

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
