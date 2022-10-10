package com.diskusage.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias RouteWithArguments = Pair<String, Any?>

class NavController(startRoute: String) {
    private val routesSink = MutableStateFlow(listOf<RouteWithArguments>(startRoute to null))

    val routesFlow = routesSink.asStateFlow()

    fun navigate(route: String, arguments: Any? = null) =
        routesSink.tryEmit(routesSink.value + (route to arguments))

    fun popBack(upToRoute: String? = null) = if (upToRoute == null) {
        routesSink.tryEmit(routesSink.value.dropLast(1))
    } else {
        routesSink.tryEmit(routesSink.value.dropLastWhile { it.first != upToRoute })
    }
}
