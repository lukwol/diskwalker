package com.diskusage.libraries.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun ScreensNavigation(
    startRoute: ScreenRoute,
    builder: ScreensMapBuilder.() -> Unit
) {
    val mapBuilder = ScreensMapBuilder()
    builder(mapBuilder)

    val screensMap = remember { mapBuilder.build() }
    val screensController = remember { ScreensControllerImpl(startRoute) }

    val routesWithArguments by screensController.routesState
    val (route, arguments) = routesWithArguments.last()

    CompositionLocalProvider(
        LocalScreensController provides screensController
    ) {
        screensMap.getValue(route)(arguments)
    }
}
