package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import com.diskusage.libraries.navigation.NavHost
import com.diskusage.libraries.navigation.WindowsHost
import com.diskusage.libraries.viewmodel.navigation.composable
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

fun AppNavigation() {
//    singleWindowApplication {
//        NavHost(
//            startRoute = AppRoutes.GreetingScreen
//        ) {
//            composable(
//                route = AppRoutes.GreetingScreen,
//                viewModelFactory = { GreetingScreenViewModel() }
//            ) { viewModel ->
//                GreetingScreen(viewModel)
//            }
//
//            composable(
//                route = AppRoutes.GreeterScreen,
//                viewModelFactory = { GreeterViewModel(it as String) }
//            ) { viewModel ->
//                GreeterScreen(viewModel)
//            }
//        }
//    }
    application {
        WindowsHost(
            startWindow = AppRoutes.Windows.First
        ) {
            window(AppRoutes.Windows.First) {
                NavHost(
                    startRoute = AppRoutes.Screens.Greeting
                ) {
                    composable(
                        route = AppRoutes.Screens.Greeting,
                        viewModelFactory = { GreetingScreenViewModel() }
                    ) { viewModel ->
                        GreetingScreen(viewModel)
                    }

                    composable(
                        route = AppRoutes.Screens.Greeter,
                        viewModelFactory = { GreeterViewModel(it as String) }
                    ) { viewModel ->
                        GreeterScreen(viewModel)
                    }
                }
            }

            window(AppRoutes.Windows.Second) {
                NavHost(
                    startRoute = AppRoutes.Screens.Greeting
                ) {
                    composable(
                        route = AppRoutes.Screens.Greeting,
                        viewModelFactory = { GreetingScreenViewModel() }
                    ) { viewModel ->
                        GreetingScreen(viewModel)
                    }

                    composable(
                        route = AppRoutes.Screens.Greeter,
                        viewModelFactory = { GreeterViewModel(it as String) }
                    ) { viewModel ->
                        GreeterScreen(viewModel)
                    }
                }
            }
        }
    }
}
