package com.diskusage.presentation.di

import com.diskusage.presentation.screens.chart.ChartViewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { ChartViewModel(get(), get()) }
}
