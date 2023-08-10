package com.diskwalker.domain.usecases.chart.item.arc

import com.diskwalker.domain.common.Constants.Chart.BigArcWidth
import com.diskwalker.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskwalker.domain.common.Constants.Chart.SmallArcWidth

/**
 * Calculates the width of an [Arc][com.diskwalker.domain.model.Arc] based on `itemDepth`
 */
class GetArcWidth {
    operator fun invoke(itemDepth: Int) = when {
        itemDepth == 0 -> 0f
        itemDepth <= MaxBigArcsDepth -> BigArcWidth
        else -> SmallArcWidth
    }
}
