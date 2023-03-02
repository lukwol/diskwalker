package com.diskusage.libraries.support.di

import com.diskusage.libraries.support.SupportJni
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val supportLibraryModule = module {
    singleOf(::SupportJni)
}
