package com.diskusage.presentation.navigation

import androidx.compose.runtime.Composable

private typealias Content = @Composable (NavArguments?) -> Unit

class NavMapBuilder {
    private val destinations = mutableMapOf<NavRoute, Content>()

    fun composable(
        route: NavRoute,
        content: Content
    ) {
        destinations[route] = content
    }

    fun build() = destinations.toMap()
}
