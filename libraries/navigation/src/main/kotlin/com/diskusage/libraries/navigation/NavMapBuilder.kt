package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable

class NavMapBuilder {
    private val destinations = mutableMapOf<NavRoute, @Composable (NavArguments?) -> Unit>()

    fun composable(
        route: NavRoute,
        content: @Composable (NavArguments?) -> Unit
    ) {
        destinations[route] = content
    }

    fun build() = destinations.toMap()
}
