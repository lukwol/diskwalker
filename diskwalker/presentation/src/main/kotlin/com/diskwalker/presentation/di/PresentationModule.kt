package com.diskwalker.presentation.di

import com.diskwalker.presentation.screens.chart.ChartViewModel
import com.diskwalker.presentation.screens.dashboard.DashboardViewModel
import com.diskwalker.presentation.screens.scan.ScanViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val presentationModule = module {
    factoryOf(::DashboardViewModel)
    factoryOf(::ScanViewModel)
    factoryOf(::ChartViewModel)
}
