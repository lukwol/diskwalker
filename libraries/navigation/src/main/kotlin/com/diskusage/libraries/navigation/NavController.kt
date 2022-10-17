package com.diskusage.libraries.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavController(startRoute: NavRoute) {
    private val routesSink = MutableStateFlow(listOf(RouteWithArguments(startRoute)))

    internal val routesFlow = routesSink.asStateFlow()

    val routes get() = routesFlow.value.map(RouteWithArguments::route)

    fun push(route: NavRoute, arguments: NavArguments? = null) =
        routesSink.tryEmit(routesSink.value + RouteWithArguments(route, arguments))

    fun pop(upToRoute: NavRoute? = null) = if (upToRoute == null) {
        routesSink.tryEmit(routesSink.value.dropLast(1))
    } else {
        routesSink.tryEmit(routesSink.value.dropLastWhile { it.route != upToRoute })
    }

    fun popToRoot() = routesSink.tryEmit(routesSink.value.take(1))
}
