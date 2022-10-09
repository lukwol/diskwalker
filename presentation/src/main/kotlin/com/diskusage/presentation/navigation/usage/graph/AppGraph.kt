package com.diskusage.presentation.navigation.usage.graph

import androidx.compose.runtime.Composable
import com.diskusage.presentation.navigation.NavHost
import com.diskusage.presentation.navigation.usage.screens.FirstScreen
import com.diskusage.presentation.navigation.usage.screens.SecondScreen

@Composable
fun AppGraph() {
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
