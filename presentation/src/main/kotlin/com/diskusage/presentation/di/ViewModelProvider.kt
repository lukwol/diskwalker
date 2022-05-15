package com.diskusage.presentation.di

import com.diskusage.presentation.screens.chart.ChartViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ViewModelProvider : KoinComponent {
    val chartViewModel: ChartViewModel get() = get()
}
