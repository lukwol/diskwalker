package com.diskusage.app

import ScanResultComponent
import SupportLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.data.di.dataModule
import com.diskusage.domain.di.domainModule
import com.diskusage.libraries.support.di.supportLibraryModule
import com.diskusage.presentation.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun main() {
    startKoin {
        printLogger(Level.ERROR)
        modules(
            supportLibraryModule,
            dataModule,
            domainModule,
            presentationModule,
        )
    }
    singleWindowApplication {
        var isSupportLibraryLoaded by remember { mutableStateOf(false) }

        ScanResultComponent(isSupportLibraryLoaded)

        LaunchedEffect(Unit) {
            SupportLibrary.loadLibrary()
            isSupportLibraryLoaded = true
        }
    }
}
