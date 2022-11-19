package com.diskusage.presentation.screens.chart

import com.diskusage.domain.model.ChartData
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListData

data class ChartViewState(
    val diskEntry: DiskEntry,
    val listData: ListData? = null,
    val chartData: ChartData? = null
)
