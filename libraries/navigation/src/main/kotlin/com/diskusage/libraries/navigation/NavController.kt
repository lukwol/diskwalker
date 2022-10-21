package com.diskusage.libraries.navigation

import androidx.compose.runtime.mutableStateOf

class NavController(startRoute: NavRoute) {
    internal val routesState = mutableStateOf(listOf(RouteWithArguments(startRoute)))

    val routes get() = routesState.value.map(RouteWithArguments::route)

    fun push(route: NavRoute, arguments: NavArguments? = null) {
        routesState.value += RouteWithArguments(route, arguments)
    }

    fun pop(upToRoute: NavRoute? = null) = if (upToRoute == null) {
        routesState.value = routesState.value.dropLast(1)
    } else {
        routesState.value = routesState.value.dropLastWhile { it.route != upToRoute }
    }

    fun popToRoot() {
        routesState.value = routesState.value.take(1)
    }
}
