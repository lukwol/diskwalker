package com.diskusage.presentation.di

import com.diskusage.presentation.screens.chart.ChartViewModel
import com.diskusage.presentation.screens.scan.ScanViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val presentationModule = module {
    factoryOf(::ScanViewModel)
    factoryOf(::ChartViewModel)
}
