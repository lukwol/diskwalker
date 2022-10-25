package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.singleWindowApplication
import com.diskusage.libraries.navigation.LocalWindowController
import com.diskusage.libraries.navigation.NavHost
import com.diskusage.libraries.navigation.WindowsHost
import com.diskusage.libraries.viewmodel.navigation.composable
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

fun AppNavigation() {
    singleWindowApplication {
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
//    application {
//        WindowsHost(
//            startWindow = AppWindows.GreetWindow
//        ) {
//            window(AppWindows.GreetWindow) {
//                NavHost(
//                    startRoute = AppRoutes.GreetingScreen
//                ) {
//                    composable(
//                        route = AppRoutes.GreetingScreen,
//                        viewModelFactory = { GreetingScreenViewModel() }
//                    ) { viewModel ->
//                        GreetingScreen(viewModel)
//                    }
//
//                    composable(
//                        route = AppRoutes.GreeterScreen,
//                        viewModelFactory = { GreeterViewModel(it as String) }
//                    ) { viewModel ->
//                        GreeterScreen(viewModel)
//                    }
//                }
//            }
//
//            window(
//                AppWindows.OtherWindow,
//                windowFactory = {
//                    Window(
//                        title = "Fooo",
//                        onCloseRequest = ::exitApplication,
//                        content = it
//                    )
//                }
//            ) {
//                NavHost(
//                    startRoute = AppRoutes.GreetingScreen
//                ) {
//                    composable(
//                        route = AppRoutes.GreetingScreen,
//                        viewModelFactory = { GreetingScreenViewModel() }
//                    ) { viewModel ->
//                        GreetingScreen(viewModel)
//                    }
//
//                    composable(
//                        route = AppRoutes.GreeterScreen,
//                        viewModelFactory = { GreeterViewModel(it as String) }
//                    ) { viewModel ->
//                        GreeterScreen(viewModel)
//                    }
//                }
//            }
//        }
//    }
}
