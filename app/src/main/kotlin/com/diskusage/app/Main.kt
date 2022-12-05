package com.diskusage.app

import SupportLibrary
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.data.di.dataModule
import com.diskusage.domain.di.domainModule
import com.diskusage.libraries.support.di.supportLibraryModule
import com.diskusage.presentation.di.presentationModule
import com.diskusage.presentation.navigation.AppNavigation
import com.diskusage.presentation.theme.AppTheme
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun main() {
    SupportLibrary.loadLibrary()

    startKoin {
        printLogger(Level.INFO)
        modules(
            supportLibraryModule,
            dataModule,
            domainModule,
            presentationModule
        )
    }

    singleWindowApplication {
        AppTheme {
            AppNavigation()
        }
    }
}
