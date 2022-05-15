package com.diskusage.data.di

import FileSizeFFI
import com.diskusage.data.datasources.DiskEntryDataSourceImpl
import com.diskusage.domain.datasources.DiskEntryDataSource
import com.diskusage.support.FileSize
import org.koin.dsl.module

val dataModule = module {
    single<FileSize> { FileSizeFFI }
    single<DiskEntryDataSource> { DiskEntryDataSourceImpl(get()) }
}
