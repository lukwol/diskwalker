package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import com.diskusage.presentation.navigation.NavHost
import com.diskusage.presentation.navigation.usage.screens.FirstScreen
import com.diskusage.presentation.navigation.usage.screens.SecondScreen

@Composable
fun AppNavigation() {
    NavHost(
        startRoute = AppRoutes.FirstScreen
    ) {
        composable(AppRoutes.FirstScreen) {
            FirstScreen()
        }

        composable(AppRoutes.SecondScreen) {
            SecondScreen()
        }
    }
}
