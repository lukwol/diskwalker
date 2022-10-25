package com.diskusage.libraries.navigation.windows

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window

class WindowsMapBuilder {
    private val windows = mutableMapOf<String, @Composable () -> Unit>()

    fun window(
        route: String,
        title: String = route,
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
        route: String,
        windowFactory: @Composable (@Composable FrameWindowScope.() -> Unit) -> Unit,
        content: @Composable FrameWindowScope.() -> Unit
    ) {
        windows[route] = {
            windowFactory { content() }
        }
    }

    fun build() = windows.toMap()
}
