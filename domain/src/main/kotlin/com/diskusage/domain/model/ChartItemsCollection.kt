package com.diskusage.domain.model

// TODO: documentation

data class ChartItemsCollection(
    val startItems: List<ChartItem>,
    val endItems: List<ChartItem>? = null
)
