package com.diskwalker.presentation.di

import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.presentation.screens.chart.ChartViewModel
import com.diskwalker.presentation.screens.dashboard.DashboardViewModel
import com.diskwalker.presentation.screens.scan.ScanViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.nio.file.Path

object ViewModelProvider : KoinComponent {
    fun getDashboardViewModel(): DashboardViewModel = get()
    fun getScanViewModel(diskInfo: DiskInfo): ScanViewModel = get(parameters = { parametersOf(diskInfo) })
    fun getChartViewModel(path: Path): ChartViewModel = get(parameters = { parametersOf(path) })
}
