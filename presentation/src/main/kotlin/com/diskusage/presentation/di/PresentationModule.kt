package com.diskusage.presentation.di

import com.diskusage.presentation.screens.chart.ChartViewModel
import com.diskusage.presentation.screens.dashboard.DashboardViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val presentationModule = module {
    factoryOf(::DashboardViewModel)
    factoryOf(::ChartViewModel)
}
