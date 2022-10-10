package com.diskusage.presentation.navigation

import androidx.compose.runtime.*

@Composable
fun NavHost(
    startRoute: NavRoute,
    builder: NavDestinationBuilder.() -> Unit
) {
    val destinationsBuilder = NavDestinationBuilder()
    destinationsBuilder.builder()

    val destinations = remember { destinationsBuilder.build() }
    val navController = remember { NavController(startRoute) }

    val routes by navController.routesFlow.collectAsState()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        destinations.getValue(routes.last())()
    }
}
