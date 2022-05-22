package com.diskusage.domain.di

import com.diskusage.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    single { GetArc(get(), get(), get(), get()) }
    single { GetChartItem(get(), get(), get()) }
    single { GetChartItems(get(), get(), get()) }
    single { GetColor(get(), get(), get()) }
    single { GetDepth(get(), get()) }
    single { GetDiskEntries(get()) }
    single { GetRelationship() }
    single { GetRoot() }
    single { GetStartAngle(get(), get(), get()) }
    single { GetSweepAngle(get(), get()) }
    single { IncludeDiskEntry(get(), get()) }
    single { SortDiskEntries() }
}
