package com.diskusage.libraries.navigation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NavHost(
    startRoute: String,
    builder: NavMapBuilder.() -> Unit
) {
    val mapBuilder = NavMapBuilder()
    builder(mapBuilder)

    val navigationMap = remember { mapBuilder.build() }
    val navController = remember { NavControllerImpl(startRoute) }

    val routesWithArguments by navController.routesState
    val (route, arguments) = routesWithArguments.last()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        navigationMap.getValue(route)(arguments)
    }
}
