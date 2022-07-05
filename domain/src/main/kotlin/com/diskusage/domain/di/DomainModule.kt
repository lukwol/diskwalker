package com.diskusage.domain.di

import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.GetColor
import com.diskusage.domain.usecases.chart.chartitem.GetSortedChartItems
import com.diskusage.domain.usecases.chart.chartitem.arc.*
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot
import org.koin.dsl.module

val domainModule = module {
    single { GetArc(get(), get(), get(), get(), get()) }
    single { GetStartAngle(get(), get(), get()) }
    single { GetSweepAngle(get(), get()) }
    single { GetStartRadius(get(), get()) }
    single { IsArcSelected() }
    single { GetSortedChartItems(get(), get(), get(), get(), get()) }
    single { GetColor(get(), get(), get(), get()) }
    single { GetDiskEntriesList(get()) }
    single { IncludeDiskEntry(get(), get()) }
    single { SortDiskEntries() }
    single { GetDepth(get(), get()) }
    single { GetRelationship() }
    single { GetRoot() }
}
