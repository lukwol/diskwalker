package com.diskusage.libraries.navigation.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun WindowsNavigation(
    startRoute: WindowRoute,
    builder: WindowsMapBuilder.() -> Unit
) {
    val mapBuilder = WindowsMapBuilder()
    builder(mapBuilder)

    val windowsMap = remember { mapBuilder.build() }
    val windowsController = remember { WindowsControllerImpl(startRoute) }

    val routes by windowsController.routesState

    CompositionLocalProvider(
        LocalWindowController provides windowsController
    ) {
        windowsMap.forEach { (route, content) ->
            @Composable
            fun window() {
                if (route in routes) {
                    content()
                }
            }
            window()
        }
    }
}
