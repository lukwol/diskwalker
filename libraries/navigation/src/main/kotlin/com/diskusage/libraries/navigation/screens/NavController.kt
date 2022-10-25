package com.diskusage.libraries.navigation.screens

import androidx.compose.runtime.mutableStateOf

interface NavController {
    val routes: List<String>
    fun push(route: String, arguments: NavArguments? = null)
    fun pop(upToRoute: String? = null)
}

internal class NavControllerImpl(startRoute: String) : NavController {
    internal val routesState = mutableStateOf(listOf(RouteWithArguments(startRoute)))

    override val routes get() = routesState.value.map(RouteWithArguments::route)

    override fun push(route: String, arguments: NavArguments?) {
        routesState.value += RouteWithArguments(route, arguments)
    }

    override fun pop(upToRoute: String?) {
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
    override val routes: List<String> = emptyList()

    override fun push(route: String, arguments: NavArguments?) = Unit

    override fun pop(upToRoute: String?) = Unit
}
