package com.diskusage.presentation.di

import com.diskusage.presentation.screens.chart.ChartViewModel
import com.diskusage.presentation.screens.dashboard.DashboardViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.nio.file.Path

object ViewModelProvider : KoinComponent {
    fun getDashboardViewModel(): DashboardViewModel = get()
    fun getChartViewModel(path: Path): ChartViewModel = get(parameters = { parametersOf(path) })
}
