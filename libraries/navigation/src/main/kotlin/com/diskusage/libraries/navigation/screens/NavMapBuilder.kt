package com.diskusage.libraries.navigation.screens

import androidx.compose.runtime.Composable

class NavMapBuilder {
    private val destinations = mutableMapOf<String, @Composable (NavArguments?) -> Unit>()

    fun composable(
        route: String,
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
