package com.diskwalker.app

import SupportLibrary
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.singleWindowApplication
import com.diskwalker.data.di.dataModule
import com.diskwalker.domain.di.domainModule
import com.diskwalker.libraries.support.di.supportLibraryModule
import com.diskwalker.presentation.di.presentationModule
import com.diskwalker.presentation.navigation.AppNavigation
import com.diskwalker.presentation.theme.AppTheme
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
            presentationModule,
        )
    }

    singleWindowApplication(
        title = "DiskWalker",
    ) {
        AppTheme {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize(),
            ) {
                AppNavigation()
            }
        }
    }
}
