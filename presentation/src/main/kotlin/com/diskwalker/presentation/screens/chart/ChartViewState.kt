package com.diskwalker.presentation.screens.chart

import com.diskwalker.domain.model.chart.ChartData
import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.domain.model.list.ListData
import com.diskwalker.domain.model.path.PathInfo
import java.nio.file.Path

data class ChartViewState(
    val disk: Path,
    val path: Path,
    val pathInfo: PathInfo,
    val diskInfo: DiskInfo,
    val listData: ListData? = null,
    val chartData: ChartData? = null,
)
