package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope

class NavMapBuilder(val window: String) {
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
    private val windows = mutableMapOf<String, @Composable FrameWindowScope.() -> Unit>()

    fun window(
        title: String,
        // TODO: Pass window options
        content: @Composable FrameWindowScope.() -> Unit
    ) {
        windows[title] = content
    }

    fun build() = windows.toMap()
}
