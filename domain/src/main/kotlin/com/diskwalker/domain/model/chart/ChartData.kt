package com.diskwalker.domain.model.chart

/**
 * A pair of lists for chart to be able to animate from one to another.
 */
data class ChartData(
    val startItems: List<ChartItem>,
    val endItems: List<ChartItem>? = null,
)
