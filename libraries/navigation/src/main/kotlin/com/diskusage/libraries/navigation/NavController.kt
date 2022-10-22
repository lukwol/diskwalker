package com.diskusage.libraries.navigation

import androidx.compose.runtime.mutableStateOf

class NavController(startRoute: NavRoute) {
    internal val routesState = mutableStateOf(listOf(RouteWithArguments(startRoute)))

    val routes get() = routesState.value.map(RouteWithArguments::route)

    fun push(route: NavRoute, arguments: NavArguments? = null) {
        routesState.value += RouteWithArguments(route, arguments)
    }

    fun pop(upToRoute: NavRoute? = null) = when {
        upToRoute != null && upToRoute !in routes -> throw IllegalArgumentException("There is no $upToRoute on the stack")
        upToRoute == routes.last() -> throw IllegalArgumentException("Cannot pop up to current route $upToRoute")
        routes.size == 1 -> throw IllegalStateException("Cannot pop start route")
        upToRoute == null -> routesState.value = routesState.value.dropLast(1)
        else -> routesState.value = routesState.value.dropLastWhile { it.route != upToRoute }
    }
}
