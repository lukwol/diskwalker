package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import com.diskusage.libraries.navigation.NavHost
import com.diskusage.libraries.viewmodel.navigation.composable
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

@Composable
fun AppNavigation() {
    NavHost(
        startRoute = AppRoutes.FirstScreen
    ) {
        composable(
            route = AppRoutes.FirstScreen,
            viewModelFactory = { GreetingScreenViewModel() }
        ) {
            GreetingScreen(it)
        }

        composable(
            route = AppRoutes.SecondScreen,
            viewModelFactory = { GreeterViewModel(it as String) }
        ) {
            GreeterScreen(it)
        }
    }
}

/*
 *    ┌─────┐    depends on
 *    │ MVI ◄────────────────────┐
 *    └──┬──┘                    │
 *       │            ┌──────────┴─────┐
 *       │            │ MVI Navigation │
 *       │depends on  └──────────┬─────┘
 *       │                       │
 *       │                       │
 * ┌─────▼──────┐                │depends on
 * │ View Model ◄───────────┐    │
 * └────────────┘ depends on│    │
 *                          │    │
 *                    ┌─────┴────▼────┐
 *                    │ VM Navigation │
 *                    └─────┬─────────┘
 *                          │
 * ┌────────────┐ depends on│
 * │ Navigation ◄───────────┘
 * └────────────┘
 */
