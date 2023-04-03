package com.diskwalker.data.di

import com.diskwalker.data.repositories.ScanRepositoryImpl
import com.diskwalker.data.services.FileSizeServiceImpl
import com.diskwalker.data.services.ScanService
import com.diskwalker.data.services.SystemInfoServiceImpl
import com.diskwalker.domain.repositories.ScanRepository
import com.diskwalker.domain.services.FileSizeService
import com.diskwalker.domain.services.SystemInfoService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::FileSizeServiceImpl) { bind<FileSizeService>() }
    singleOf(::SystemInfoServiceImpl) { bind<SystemInfoService>() }
    singleOf(::ScanRepositoryImpl) { bind<ScanRepository>() }
    singleOf(::ScanService)
}
