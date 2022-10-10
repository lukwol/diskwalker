package com.diskusage.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavController(startRoute: NavRoute) {
    private val routesSink = MutableStateFlow(listOf(startRoute))

    val routesFlow = routesSink.asStateFlow()

    fun navigate(route: NavRoute) = routesSink.tryEmit(routesSink.value + route)

    fun popBack(upToRoute: NavRoute? = null) = if (upToRoute == null) {
        routesSink.tryEmit(routesSink.value.dropLast(1))
    } else {
        routesSink.tryEmit(routesSink.value.dropLastWhile { it != upToRoute })
    }
}
