package com.diskusage.app

import ScanResultComponent
import SupportLibrary
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
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
            presentationModule
        )
    }
    singleWindowApplication {
        var isSupportLibraryLoaded by remember { mutableStateOf(false) }

        MaterialTheme(
            colors = darkColors(
                background = Color(0xFF363736)
            )
        ) {
            ScanResultComponent(isSupportLibraryLoaded)
        }

        LaunchedEffect(Unit) {
            SupportLibrary.loadLibrary()
            isSupportLibraryLoaded = true
        }
    }
}
