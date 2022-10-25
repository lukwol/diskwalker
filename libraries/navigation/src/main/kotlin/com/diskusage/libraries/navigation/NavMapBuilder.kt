package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window

class NavMapBuilder(val windowRoute: String) {
    private val destinations = mutableMapOf<NavRoute, @Composable (NavArguments?) -> Unit>()

    fun composable(
        route: NavRoute,
        content: @Composable (args: NavArguments?) -> Unit
    ) {
        if (destinations.containsKey(route)) {
            throw IllegalArgumentException("Route: $route is already registered")
        } else {
            destinations[route] = content
        }
    }

    fun build() = destinations.toMap()
}

class WindowsMapBuilder {
    private val windows = mutableMapOf<String, @Composable () -> Unit>()

    fun window(
        title: String,
        content: @Composable FrameWindowScope.() -> Unit
    ) {
        customWindow(route = title) {
            val windowsController = LocalWindowController.current

            Window(
                title = title,
                onCloseRequest = {
                    windowsController.close(title)
                },
                content = content
            )
        }
    }

    fun customWindow(
        route: String,
        window: @Composable () -> Unit
    ) {
        windows[route] = window
    }

    fun build() = windows.toMap()
}
