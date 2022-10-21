package com.diskusage.libraries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NavHost(
    startRoute: NavRoute,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder()
    mapBuilder.builder()

    val navigationMap = remember { mapBuilder.build() }
    val navController = remember { NavController(startRoute) }

    val routesWithArguments by navController.routesState
    val (route, arguments) = routesWithArguments.last()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        navigationMap.getValue(route)(arguments)
    }
}
