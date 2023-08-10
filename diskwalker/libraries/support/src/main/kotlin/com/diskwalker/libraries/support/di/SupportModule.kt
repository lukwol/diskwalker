package com.diskwalker.libraries.support.di

import com.diskwalker.libraries.support.SupportJni
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val supportModule = module {
    singleOf(::SupportJni)
}
