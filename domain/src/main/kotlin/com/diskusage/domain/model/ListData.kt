package com.diskusage.domain.model

/**
 * `Parent` [ChartItem] and a list of it's [`child items`][ChartItem].
 */
data class ListData(
    val parentItem: ChartItem,
    val childItems: List<ChartItem>
)
