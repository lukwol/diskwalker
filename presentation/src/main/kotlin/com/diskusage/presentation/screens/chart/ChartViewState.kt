package com.diskusage.presentation.screens.chart

import com.diskusage.domain.model.ChartData
import com.diskusage.domain.model.ListData
import java.nio.file.Path

data class ChartViewState(
    val path: Path,
    val listData: ListData? = null,
    val chartData: ChartData? = null
)
