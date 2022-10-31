package com.diskusage.libraries.windows.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window

/**
 * Builds windows navigation map.
 */
class WindowsMapBuilder {

    /**
     * Map of all registered [windows][WindowRoute] to their [content][Composable].
     */
    private val windowsMap = mutableMapOf<WindowRoute, @Composable () -> Unit>()

    /**
     * Declare window [content] for [route] and add it to [windowsMap].
     *
     * @param route [WindowRoute] used to navigate to the [window]
     * @param content [Composable] content of the window
     *
     * @throws IllegalArgumentException when adding window for already registered [route]
     */
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

    /**
     * Declare window [content] for [route] with custom [Window].
     *
     * @param route [WindowRoute] used to navigate to the [window]
     * @param windowFactory lambda that takes [content] argument and wraps it in custom [Window].
     * @param content [Composable] content of the window
     *
     * @throws IllegalArgumentException when adding window for already registered [route]
     *
     * @see WindowsMapBuilder.window
     */
    fun window(
        route: WindowRoute,
        windowFactory: @Composable (content: @Composable FrameWindowScope.() -> Unit) -> Unit,
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

    /**
     * Build immutable [windowsMap].
     */
    fun build() = windowsMap.toMap()
}
