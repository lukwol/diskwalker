package com.diskusage.app

import SupportLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.data.di.dataModule
import com.diskusage.domain.di.domainModule
import com.diskusage.libraries.support.di.supportLibraryModule
import com.diskusage.presentation.di.presentationModule
import com.diskusage.presentation.screens.scanresult.ScanResultComponent
import com.diskusage.presentation.theme.AppTheme
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun main() {
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
        var isSupportLibraryLoaded by remember { mutableStateOf(false) }

        AppTheme {
            ScanResultComponent(isSupportLibraryLoaded)
        }

        LaunchedEffect(Unit) {
            SupportLibrary.loadLibrary()
            isSupportLibraryLoaded = true
        }
    }
}
