package com.diskusage.data.di

import FileSizeFFI
import com.diskusage.data.repositories.DiskEntryRepositoryImpl
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.support.FileSize
import org.koin.dsl.module

val dataModule = module {
    single<FileSize> { FileSizeFFI }
    single<DiskEntryRepository> { DiskEntryRepositoryImpl(get()) }
}
