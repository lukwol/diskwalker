package com.diskusage.presentation.navigation

import androidx.compose.runtime.Composable

class NavDestinationBuilder {
    private val destinations: MutableMap<NavRoute, @Composable () -> Unit> = mutableMapOf()

    fun composable(
        route: NavRoute,
        content: @Composable () -> Unit
    ) {
        destinations[route] = content
    }

    fun build() = destinations.toMap()
}
