package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import com.diskusage.libraries.navigation.NavHost
import com.diskusage.libraries.viewmodel.navigation.mvvm
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

@Composable
fun AppNavigation() {
    NavHost(
        startRoute = AppRoutes.FirstScreen
    ) {
        mvvm(
            route = AppRoutes.FirstScreen,
            viewModelFactory = { GreetingScreenViewModel() }
        ) {
            GreetingScreen(it)
        }

        mvvm(
            route = AppRoutes.SecondScreen,
            viewModelFactory = { GreeterViewModel(it as String) }
        ) {
            GreeterScreen(it)
        }
    }
}

/*
 *              ┌───────────────┐
 *        ┌─────┤ VM Navigation ├──────┐
 *        │     └───────────────┘      │
 *        │                            │
 *        │ depends on                 │ depends on
 *        │                            │
 * ┌──────▼─────┐               ┌──────▼─────┐
 * │ View Model │               │ Navigation │
 * └────────────┘               └────────────┘
 */
