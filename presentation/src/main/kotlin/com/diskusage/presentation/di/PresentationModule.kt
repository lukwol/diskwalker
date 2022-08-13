package com.diskusage.presentation.di

import com.diskusage.presentation.components.chart.ChartViewModel
import com.diskusage.presentation.components.scanresult.ScanResultViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val presentationModule = module {
    factoryOf(::ScanResultViewModel)
    factoryOf(::ChartViewModel)
}
