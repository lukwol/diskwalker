package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.diskusage.libraries.navigation.screens.NavHost
import com.diskusage.libraries.navigation.windows.LocalWindowController
import com.diskusage.libraries.navigation.windows.WindowsHost
import com.diskusage.libraries.viewmodel.navigation.composable
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

fun AppNavigation() {
//    singleWindowApplication {
//        NavHost(
//            startRoute = AppRoutes.Screens.Greeting
//        ) {
//            composable(
//                route = AppRoutes.Screens.Greeting,
//                viewModelFactory = { GreetingScreenViewModel() }
//            ) { viewModel ->
//                GreetingScreen(viewModel)
//            }
//
//            composable(
//                route = AppRoutes.Screens.Greeter,
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
            window(
                route = AppRoutes.Windows.First,
                title = "First Window"
            ) {
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

            window(
                route = AppRoutes.Windows.Second,
                windowFactory = {
                    val windowsController = LocalWindowController.current

                    Window(
                        title = "Fooo",
                        onCloseRequest = { windowsController.close(AppRoutes.Windows.Second) },
                        content = it
                    )
                }
            ) {
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
