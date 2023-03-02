package com.diskusage.data.di

import com.diskusage.data.repositories.ScanRepositoryImpl
import com.diskusage.data.services.FileSizeServiceImpl
import com.diskusage.data.services.SystemInfoServiceImpl
import com.diskusage.domain.repositories.ScanRepository
import com.diskusage.domain.services.FileSizeService
import com.diskusage.domain.services.SystemInfoService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::FileSizeServiceImpl) { bind<FileSizeService>() }
    singleOf(::SystemInfoServiceImpl) { bind<SystemInfoService>() }
    singleOf(::ScanRepositoryImpl) { bind<ScanRepository>() }
}
