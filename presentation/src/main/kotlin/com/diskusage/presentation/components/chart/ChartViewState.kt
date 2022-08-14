package com.diskusage.presentation.components.chart

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.entities.ListItem

data class ChartViewState(
    val diskEntry: DiskEntry,
    val listItems: List<ListItem> = listOf(),
    val startItems: List<ChartItem> = listOf(),
    val endItems: List<ChartItem>? = null
)
