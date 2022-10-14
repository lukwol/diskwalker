package com.diskusage.presentation.navigation

data class RouteWithArguments(
    val route: NavRoute,
    val arguments: NavArguments? = null
)
