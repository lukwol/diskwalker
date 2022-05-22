package com.diskusage.domain.di

import com.diskusage.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetRoot() }
    factory { GetRelationship() }
    factory { GetDiskEntries(get()) }
    factory { GetStartAngle(get(), get()) }
    factory { GetSweepAngle(get(), get()) }
    factory { GetDepth(get(), get()) }
    factory { GetArc(get(), get(), get(), get()) }
    factory { GetColor(get(), get(), get()) }
    factory { IncludeDiskEntry(get(), get()) }
    factory { GetChartItem(get(), get(), get()) }
    factory { GetChartItems(get(), get()) }
}
