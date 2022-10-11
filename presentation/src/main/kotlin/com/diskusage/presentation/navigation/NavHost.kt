package com.diskusage.presentation.navigation

import androidx.compose.runtime.*

@Composable
fun NavHost(
    startRoute: NavRoute,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder()
    mapBuilder.builder()

    val navigationMap = remember { mapBuilder.build() }
    val navController = remember { NavController(startRoute) }

    val routesWithArguments by navController.routesFlow.collectAsState()
    val (route, arguments) = routesWithArguments.last()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        navigationMap.getValue(route)(arguments)
    }
}
