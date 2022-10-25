package com.diskusage.libraries.navigation

internal data class RouteWithArguments(
    val route: String,
    val arguments: NavArguments? = null
)
