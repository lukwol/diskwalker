package com.diskusage.libraries.navigation.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun WindowsHost(
    startWindow: String,
    builder: WindowsMapBuilder.() -> Unit
) {
    val mapBuilder = WindowsMapBuilder()
    builder(mapBuilder)

    val windowsMap = remember { mapBuilder.build() }
    val windowsController = remember { WindowsControllerImpl(startWindow) }

    val windows by windowsController.windowsState

    CompositionLocalProvider(
        LocalWindowController provides windowsController
    ) {
        windowsMap.forEach { (window, content) ->
            @Composable
            fun window() {
                if (window in windows) {
                    content()
                }
            }
            window()
        }
    }
}
