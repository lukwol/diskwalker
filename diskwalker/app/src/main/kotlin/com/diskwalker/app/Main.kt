package com.diskwalker.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.diskwalker.data.di.dataModule
import com.diskwalker.domain.common.SystemTheme
import com.diskwalker.domain.common.Theme
import com.diskwalker.domain.di.domainModule
import com.diskwalker.libraries.support.di.supportModule
import com.diskwalker.libraries.utils.os.OS
import com.diskwalker.libraries.utils.os.OsUtils
import com.diskwalker.presentation.di.presentationModule
import com.diskwalker.presentation.navigation.AppNavigation
import com.diskwalker.presentation.theme.AppTheme
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.io.File

fun main() {
    loadSupportLibrary()

    startKoin {
        printLogger(Level.INFO)
        modules(
            supportModule,
            dataModule,
            domainModule,
            presentationModule,
        )
    }

    singleWindowApplication(
        title = "DiskWalker",
        icon = BitmapPainter(useResource("diskwalker_icon.png", ::loadImageBitmap)),
        state = WindowState(
            size = DpSize(
                width = 880.dp,
                height = 660.dp,
            ),
        ),
    ) {
        SystemTheme = if (isSystemInDarkTheme()) Theme.Dark else Theme.Light

        AppTheme {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize(),
            ) {
                AppNavigation()
            }
        }
    }
}

private fun loadSupportLibrary() {
    val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
    val libFile = resourcesDir.resolve(
        when (OsUtils.OperatingSystem) {
            OS.MacOS -> "libsupport.dylib"
            OS.Windows -> "support.dll"
            OS.Linux -> "support.so"
            else -> error("Unsupported target ${OsUtils.Target}")
        },
    )
    System.load(libFile.path)
}
