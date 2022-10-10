package com.diskusage.presentation.navigation

import androidx.compose.runtime.Composable

private typealias Destination = Pair<String, @Composable (Any?) -> Unit>

class NavMapBuilder {
    private val destinations = mutableListOf<Destination>()

    fun composable(
        route: String,
        content: @Composable (Any?) -> Unit
    ) {
        destinations.add(route to content)
    }

    fun build() = destinations.toMap()
}
