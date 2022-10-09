package com.diskusage.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavController(startRoute: NavRoute) {
    private val routesSink = MutableStateFlow(startRoute)

    val routesFlow = routesSink.asStateFlow()

    fun navigate(route: NavRoute) = routesSink.tryEmit(route)
}
