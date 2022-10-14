package com.diskusage.libraries.navigation

internal data class RouteWithArguments(
    val route: NavRoute,
    val arguments: NavArguments? = null
)
