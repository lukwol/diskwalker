package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope

@Composable
fun FrameWindowScope.NavHost(
    startRoute: String,
    builder: NavMapBuilder.() -> Unit
) = NavHostImpl(
    startRoute = startRoute,
    builder = builder
)

@Composable
fun RoutedWindowScope.NavHost(
    startRoute: String,
    builder: NavMapBuilder.() -> Unit
) {
    CompositionLocalProvider(
        LocalWindowController providesDefault remember { WindowsController(windowRoute) },
    ) {
        NavHostImpl(
            startRoute = startRoute,
            builder = builder
        )
    }
}

@Composable
private fun NavHostImpl(
    startRoute: String,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder()
    builder(mapBuilder)

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
    builder(mapBuilder)

    val windowsMap = remember { mapBuilder.build() }
    val windowsController = remember { WindowsController(startWindow) }

    val windows by windowsController.windowRoutesState

    CompositionLocalProvider(
        LocalWindowController provides windowsController
    ) {
        windowsMap.forEach { (route, content) ->
            @Composable
            fun window() {
                if (route in windows) {
                    content()
                }
            }
            window()
        }
    }
}
