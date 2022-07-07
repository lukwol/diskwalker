package com.diskusage.data.di

import com.diskusage.data.repositories.DiskEntryRepositoryImpl
import com.diskusage.domain.repositories.DiskEntryRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::DiskEntryRepositoryImpl) { bind<DiskEntryRepository>() }
}
