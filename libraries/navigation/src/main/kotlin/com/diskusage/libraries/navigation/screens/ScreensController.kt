package com.diskusage.libraries.navigation.screens

import androidx.compose.runtime.mutableStateOf

interface ScreensController {
    val routes: List<ScreenRoute>
    fun push(route: ScreenRoute, arguments: Arguments? = null)
    fun pop(upToRoute: ScreenRoute? = null)
}

internal class ScreensControllerImpl(startRoute: ScreenRoute) : ScreensController {
    internal val routesState = mutableStateOf(listOf(RouteWithArguments(startRoute)))

    override val routes get() = routesState.value.map(RouteWithArguments::route)

    override fun push(route: ScreenRoute, arguments: Arguments?) {
        routesState.value += RouteWithArguments(route, arguments)
    }

    override fun pop(upToRoute: ScreenRoute?) {
        return when {
            upToRoute != null && upToRoute !in routes -> throw IllegalArgumentException("There is no $upToRoute on the stack")
            upToRoute == routes.last() -> throw IllegalArgumentException("Cannot pop up to current route $upToRoute")
            routes.size == 1 -> throw IllegalStateException("Cannot pop start route")
            upToRoute == null -> routesState.value = routesState.value.dropLast(1)
            else -> routesState.value = routesState.value.dropLastWhile { it.route != upToRoute }
        }
    }
}

internal object ScreensControllerNoOp : ScreensController {
    override val routes: List<ScreenRoute> = emptyList()

    override fun push(route: ScreenRoute, arguments: Arguments?) = Unit

    override fun pop(upToRoute: ScreenRoute?) = Unit
}
