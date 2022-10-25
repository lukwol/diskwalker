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

class RoutedWindowScope(
    val windowRoute: String,
    val frameWindowScope: FrameWindowScope
)

class WindowsMapBuilder {
    private val windows = mutableMapOf<String, @Composable () -> Unit>()

    fun window(
        title: String,
        route: String = title,
        content: @Composable RoutedWindowScope.() -> Unit
    ) {
        window(
            route = route,
            windowFactory = {
                val windowsController = LocalWindowController.current

                Window(
                    title = title,
                    onCloseRequest = {
                        windowsController.close(route)
                    },
                    content = it
                )
            },
            content = content
        )
    }

    fun window(
        route: String,
        windowFactory: @Composable (@Composable FrameWindowScope.() -> Unit) -> Unit,
        content: @Composable RoutedWindowScope.() -> Unit
    ) {
        windows[route] = {
            windowFactory {
                with(RoutedWindowScope(route, this)) {
                    content()
                }
            }
        }
    }

    fun build() = windows.toMap()
}
