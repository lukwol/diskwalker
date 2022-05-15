package com.diskusage.presentation.screens.chart

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

data class ChartViewState(
    val selectedDiskEntry: DiskEntry? = null,
    val startItems: Map<String, ChartItem> = emptyMap(),
    val endItems: Map<String, ChartItem>? = null,
)
