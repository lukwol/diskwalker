package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun FrameWindowScope.NavHost(
    startRoute: NavRoute,
    windowRoute: String = window.title,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder(windowRoute)
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

    val windows by windowsController.windowRoutesState

    CompositionLocalProvider(
        LocalWindowController provides windowsController
    ) {
        windowsMap
            .filterKeys { it in windows }
            .forEach { (_, window) ->
                window()
            }
    }
}
