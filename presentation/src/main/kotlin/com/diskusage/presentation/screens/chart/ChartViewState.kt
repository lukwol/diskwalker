package com.diskusage.presentation.screens.chart

import com.diskusage.domain.model.chart.ChartData
import com.diskusage.domain.model.disk.DiskInfo
import com.diskusage.domain.model.list.ListData
import com.diskusage.domain.model.path.PathInfo
import java.nio.file.Path

data class ChartViewState(
    val disk: Path,
    val path: Path,
    val pathInfo: PathInfo,
    val diskInfo: DiskInfo,
    val listData: ListData? = null,
    val chartData: ChartData? = null,
)
