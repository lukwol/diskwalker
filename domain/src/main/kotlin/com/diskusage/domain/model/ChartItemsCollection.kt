package com.diskusage.domain.model

/**
 * A pair of lists for chart to be able to animate from one to another.
 */
data class ChartItemsCollection(
    val startItems: List<ChartItem>,
    val endItems: List<ChartItem>? = null
)
