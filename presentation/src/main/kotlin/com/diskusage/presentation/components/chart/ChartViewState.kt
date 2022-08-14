package com.diskusage.presentation.components.chart

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

data class ChartViewState(
    val diskEntry: DiskEntry,
    val startItems: List<ChartItem> = listOf(),
    val endItems: List<ChartItem>? = null
)
