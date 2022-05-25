package com.diskusage.presentation.di

import com.diskusage.domain.entities.DiskEntry
import com.diskusage.presentation.components.chart.ChartViewModel
import com.diskusage.presentation.components.scanresult.ScanResultViewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { ScanResultViewModel(get()) }
    factory { (diskEntry: DiskEntry) -> ChartViewModel(diskEntry, get()) }
}
