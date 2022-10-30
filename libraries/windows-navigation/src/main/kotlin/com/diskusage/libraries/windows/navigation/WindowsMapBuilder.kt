package com.diskusage.libraries.windows.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window

class WindowsMapBuilder {
    private val windowsMap = mutableMapOf<WindowRoute, @Composable () -> Unit>()

    fun window(
        route: WindowRoute,
        title: String = route.value,
        content: @Composable FrameWindowScope.() -> Unit
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
        route: WindowRoute,
        windowFactory: @Composable (@Composable FrameWindowScope.() -> Unit) -> Unit,
        content: @Composable FrameWindowScope.() -> Unit
    ) {
        if (windowsMap.containsKey(route)) {
            throw IllegalArgumentException("$route is already registered")
        } else {
            windowsMap[route] = {
                windowFactory { content() }
            }
        }
    }

    fun build() = windowsMap.toMap()
}
