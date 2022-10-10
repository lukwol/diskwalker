package com.diskusage.app

import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.presentation.navigation.usage.navigation.AppNavigation

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
        AppNavigation()

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
