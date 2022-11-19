package com.diskusage.presentation.di

import com.diskusage.domain.model.DiskEntry
import com.diskusage.presentation.screens.chart.ChartViewModel
import com.diskusage.presentation.screens.scanresult.ScanResultViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

object ViewModelProvider : KoinComponent {
    fun getScanResultViewModel(): ScanResultViewModel = get()
    fun getChartViewModel(diskEntry: DiskEntry): ChartViewModel = get(parameters = { parametersOf(diskEntry) })
}
