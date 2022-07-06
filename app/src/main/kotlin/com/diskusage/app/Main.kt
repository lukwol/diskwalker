package com.diskusage.app

import ScanResultComponent
import SupportLibrary
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.data.di.dataModule
import com.diskusage.domain.di.DomainModule
import com.diskusage.presentation.di.PresentationModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

fun main() {
    startKoin {
        printLogger(Level.ERROR)
        modules(
            dataModule,
            DomainModule.module,
            PresentationModule.module,
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
