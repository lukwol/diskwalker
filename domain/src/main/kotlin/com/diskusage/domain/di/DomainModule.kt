package com.diskusage.domain.di

import com.diskusage.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetArc() }
    factory { GetColor() }
    factory { GetChartItem(get(), get()) }
    factory { GetDiskEntriesList() }
    factory { GetEndDiskEntries() }
    factory { GetStartDiskEntries() }
}
