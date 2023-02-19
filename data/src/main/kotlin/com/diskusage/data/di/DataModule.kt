package com.diskusage.data.di

import com.diskusage.data.repositories.ScanRepositoryImpl
import com.diskusage.data.services.ScanService
import com.diskusage.domain.services.ScanRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ScanService)
    singleOf(::ScanRepositoryImpl) { bind<ScanRepository>() }
}
