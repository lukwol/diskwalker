package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.diskusage.libraries.navigation.screens.ScreensNavigation
import com.diskusage.libraries.navigation.windows.LocalWindowController
import com.diskusage.libraries.navigation.windows.WindowsNavigation
import com.diskusage.libraries.viewmodel.navigation.screen
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

fun AppNavigation() {
//    singleWindowApplication {
//        ScreensNavigation(
//            startRoute = AppRoutes.GreetingScreen
//        ) {
//            screen(
//                route = AppRoutes.GreetingScreen,
//                viewModelFactory = { GreetingScreenViewModel() }
//            ) { viewModel ->
//                GreetingScreen(viewModel)
//            }
//
//            screen(
//                route = AppRoutes.GreeterScreen,
//                viewModelFactory = { GreeterViewModel(it as String) }
//            ) { viewModel ->
//                GreeterScreen(viewModel)
//            }
//        }
//    }
    application {
        WindowsNavigation(
            startRoute = AppRoutes.FirstWindow
        ) {
            window(
                route = AppRoutes.FirstWindow,
                title = "First Window"
            ) {
                ScreensNavigation(
                    startRoute = AppRoutes.GreetingScreen
                ) {
                    screen(
                        route = AppRoutes.GreetingScreen,
                        viewModelFactory = { GreetingScreenViewModel() }
                    ) { viewModel ->
                        GreetingScreen(viewModel)
                    }

                    screen(
                        route = AppRoutes.GreeterScreen,
                        viewModelFactory = { GreeterViewModel(it as String) }
                    ) { viewModel ->
                        GreeterScreen(viewModel)
                    }
                }
            }

            window(
                route = AppRoutes.SecondWindow,
                windowFactory = {
                    val windowsController = LocalWindowController.current

                    Window(
                        title = "Fooo",
                        onCloseRequest = { windowsController.close(AppRoutes.SecondWindow) },
                        content = it
                    )
                }
            ) {
                ScreensNavigation(
                    startRoute = AppRoutes.GreetingScreen
                ) {
                    screen(
                        route = AppRoutes.GreetingScreen,
                        viewModelFactory = { GreetingScreenViewModel() }
                    ) { viewModel ->
                        GreetingScreen(viewModel)
                    }

                    screen(
                        route = AppRoutes.GreeterScreen,
                        viewModelFactory = { GreeterViewModel(it as String) }
                    ) { viewModel ->
                        GreeterScreen(viewModel)
                    }
                }
            }
        }
    }
}
