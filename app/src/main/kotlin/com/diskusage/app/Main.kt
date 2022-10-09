package com.diskusage.app

import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.presentation.navigation.usage.graph.AppGraph

fun main() {
//    startKoin {
//        printLogger(Level.ERROR)
//        modules(
//            supportLibraryModule,
//            dataModule,
//            domainModule,
//            presentationModule
//        )
//    }
    singleWindowApplication {
        AppGraph()

//        var isSupportLibraryLoaded by remember { mutableStateOf(false) }
//
//        AppTheme {
//            ScanResultComponent(isSupportLibraryLoaded)
//        }
//
//        LaunchedEffect(Unit) {
//            SupportLibrary.loadLibrary()
//            isSupportLibraryLoaded = true
//        }
    }
}
