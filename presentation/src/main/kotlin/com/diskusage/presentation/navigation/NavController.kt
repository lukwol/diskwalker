package com.diskusage.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavController(startRoute: NavRoute) {
    private val routesSink = MutableStateFlow(listOf(RouteWithArguments(startRoute)))

    val routesFlow = routesSink.asStateFlow()

    fun push(route: NavRoute, arguments: NavArguments? = null) =
        routesSink.tryEmit(routesSink.value + RouteWithArguments(route, arguments))

    fun pop(upToRoute: NavRoute? = null) = if (upToRoute == null) {
        routesSink.tryEmit(routesSink.value.dropLast(1))
    } else {
        routesSink.tryEmit(routesSink.value.dropLastWhile { it.route != upToRoute })
    }

    fun popToRoot() = routesSink.tryEmit(routesSink.value.take(1))
}
