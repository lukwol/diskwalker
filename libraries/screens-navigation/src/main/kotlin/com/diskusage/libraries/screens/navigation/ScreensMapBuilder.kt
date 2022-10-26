package com.diskusage.libraries.screens.navigation

import androidx.compose.runtime.Composable

class ScreensMapBuilder {
    private val destinations = mutableMapOf<ScreenRoute, @Composable (Arguments?) -> Unit>()

    fun screen(
        route: ScreenRoute,
        content: @Composable (args: Arguments?) -> Unit
    ) {
        if (destinations.containsKey(route)) {
            throw IllegalArgumentException("$route is already registered")
        } else {
            destinations[route] = content
        }
    }

    fun build() = destinations.toMap()
}
