package com.diskusage.libraries.navigation

import androidx.compose.runtime.mutableStateOf

interface NavController {
    val routes: List<NavRoute>
    fun push(route: NavRoute, arguments: NavArguments? = null)
    fun pop(upToRoute: NavRoute? = null)
}

internal class NavControllerImpl(startRoute: NavRoute) : NavController {
    internal val routesState = mutableStateOf(listOf(RouteWithArguments(startRoute)))

    override val routes get() = routesState.value.map(RouteWithArguments::route)

    override fun push(route: NavRoute, arguments: NavArguments?) {
        routesState.value += RouteWithArguments(route, arguments)
    }

    override fun pop(upToRoute: NavRoute?) {
        return when {
            upToRoute != null && upToRoute !in routes -> throw IllegalArgumentException("There is no $upToRoute on the stack")
            upToRoute == routes.last() -> throw IllegalArgumentException("Cannot pop up to current route $upToRoute")
            routes.size == 1 -> throw IllegalStateException("Cannot pop start route")
            upToRoute == null -> routesState.value = routesState.value.dropLast(1)
            else -> routesState.value = routesState.value.dropLastWhile { it.route != upToRoute }
        }
    }
}

internal object NavControllerNoOp : NavController {
    override val routes: List<NavRoute> = emptyList()

    override fun push(route: NavRoute, arguments: NavArguments?) = Unit

    override fun pop(upToRoute: NavRoute?) = Unit
}

class WindowsController(startWindow: String) {
    internal val windowRoutesState = mutableStateOf(setOf(startWindow))

    val windowRoutes get() = windowRoutesState.value

    fun open(windowRoute: String) {
        windowRoutesState.value += windowRoute
    }

    fun close(windowRoute: String) {
        windowRoutesState.value -= windowRoute
    }
}
