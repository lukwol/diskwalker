package com.diskusage.libraries.windows.navigation

import androidx.compose.runtime.mutableStateOf

interface WindowsController {
    val routes: Set<WindowRoute>
    fun open(route: WindowRoute)
    fun close(route: WindowRoute)
}

class WindowsControllerImpl(startRoute: WindowRoute) : WindowsController {
    internal val routesState = mutableStateOf(setOf(startRoute))

    override val routes get() = routesState.value

    override fun open(route: WindowRoute) = if (route in routes) {
        throw IllegalArgumentException("Window for $route is already opened")
    } else {
        routesState.value += route
    }

    override fun close(route: WindowRoute) = if (route !in routes) {
        throw IllegalArgumentException("Window for $route is already closed")
    } else {
        routesState.value -= route
    }
}

internal object WindowsControllerNoOp : WindowsController {
    override val routes: Set<WindowRoute> = emptySet()

    override fun open(route: WindowRoute) = Unit

    override fun close(route: WindowRoute) = Unit
}
