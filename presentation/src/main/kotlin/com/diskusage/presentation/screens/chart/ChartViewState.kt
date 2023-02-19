package com.diskusage.presentation.screens.chart

import com.diskusage.domain.model.ChartData
import com.diskusage.domain.model.ListData
import com.diskusage.domain.model.disk.DiskInfo
import com.diskusage.domain.model.scan.ScanItem
import java.nio.file.Path

data class ChartViewState(
    val path: Path,
    val diskInfo: DiskInfo,
    val scanItem: ScanItem,
    val listData: ListData? = null,
    val chartData: ChartData? = null
)
