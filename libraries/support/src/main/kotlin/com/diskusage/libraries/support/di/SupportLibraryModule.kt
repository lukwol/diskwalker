package com.diskusage.libraries.support.di

import com.diskusage.domain.services.FileSizeService
import com.diskusage.libraries.support.filesize.FileSizeServiceFFI
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val supportLibraryModule = module {
    singleOf(::FileSizeServiceFFI) { bind<FileSizeService>() }
}
