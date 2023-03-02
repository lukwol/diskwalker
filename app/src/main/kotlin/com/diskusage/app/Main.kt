package com.diskusage.app

import SupportJni
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
    SupportJni.loadLibrary()

    startKoin {
        printLogger(Level.INFO)
        modules(
            supportLibraryModule,
            dataModule,
            domainModule,
            presentationModule
        )
    }

    singleWindowApplication(
        title = "DiskUsage"
    ) {
        AppTheme {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                AppNavigation()
            }
        }
    }
}
