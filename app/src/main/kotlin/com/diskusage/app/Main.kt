package com.diskusage.app

import ChartScreen
import SupportLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.data.di.dataModule
import com.diskusage.domain.di.domainModule
import com.diskusage.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun main() {
    startKoin {
        printLogger(Level.ERROR)
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }
    singleWindowApplication {
        var isSupportLibraryLoaded by remember { mutableStateOf(false) }

        ChartScreen(isSupportLibraryLoaded)

        LaunchedEffect(Unit) {
            SupportLibrary.loadLibrary()
            isSupportLibraryLoaded = true
        }
    }
}