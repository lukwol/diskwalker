package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window


@Composable
fun FrameWindowScope.NavHost(
    startRoute: NavRoute,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder(window.title)
    mapBuilder.builder()

    val navigationMap = remember { mapBuilder.build() }
    val navController = remember { NavControllerImpl(startRoute) }

    val routesWithArguments by navController.routesState
    val (route, arguments) = routesWithArguments.last()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        navigationMap.getValue(route)(arguments)
    }
}

@Composable
fun WindowsHost(
    startWindow: String,
    builder: WindowsMapBuilder.() -> Unit
) {
    val mapBuilder = WindowsMapBuilder()
    mapBuilder.builder()

    val windowsMap = remember { mapBuilder.build() }
    val windowsController = remember { WindowsController(startWindow) }

    val windows by windowsController.windowsState

    CompositionLocalProvider(
        LocalWindowController provides windowsController
    ) {
        windowsMap
            .filterKeys { it in windows }
            .forEach { (title, content) ->
                Window(
                    title = title,
                    onCloseRequest = {
                        windowsController.close(title)
                    },
                ) {
                    content()
                }
            }
    }
}
