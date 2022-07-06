package com.diskusage.libraries.support.di

import FileSizeFFI
import com.diskusage.domain.services.FileSizeService
import org.koin.core.module.Module
import org.koin.dsl.module

class SupportLibraryModule {
    val module: Module
        get() = module {
            single<FileSizeService> { FileSizeFFI }
        }
}
