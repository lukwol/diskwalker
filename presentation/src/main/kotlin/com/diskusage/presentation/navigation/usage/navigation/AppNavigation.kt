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
        startRoute = AppRoutes.GreetingScreen
    ) {
        composable(
            route = AppRoutes.GreetingScreen,
            viewModelFactory = { GreetingScreenViewModel() }
        ) { viewModel ->
            GreetingScreen(viewModel)
        }

        composable(
            route = AppRoutes.GreeterScreen,
            viewModelFactory = { GreeterViewModel(it as String) }
        ) { viewModel ->
            GreeterScreen(viewModel)
        }
    }
}
