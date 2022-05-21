package com.diskusage.domain.di

import com.diskusage.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetDiskEntries(get()) }
    factory { GetStartAngle() }
    factory { GetSweepAngle() }
    factory { GetDepth() }
    factory { GetArc(get(), get(), get()) }
    factory { GetColor(get(), get()) }
    factory { IncludeDiskEntry(get()) }
    factory { GetChartItem(get(), get()) }
    factory { GetChartItems(get(), get()) }
}
