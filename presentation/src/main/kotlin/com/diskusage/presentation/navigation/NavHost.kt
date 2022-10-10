package com.diskusage.presentation.navigation

import androidx.compose.runtime.*

@Composable
fun NavHost(
    startRoute: String,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder()
    mapBuilder.builder()

    val navigationMap = remember { mapBuilder.build() }
    val navController = remember { NavController(startRoute) }

    val routesWithArguments by navController.routesFlow.collectAsState()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        val (route, arguments) = routesWithArguments.last()
        navigationMap.getValue(route)(arguments)
    }
}
