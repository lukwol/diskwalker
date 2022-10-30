package com.diskusage.libraries.screens.navigation

/**
 * A pair of [ScreenRoute] and [Arguments].
 */
internal data class RouteWithArguments(
    val route: ScreenRoute,
    val arguments: Arguments? = null
)
