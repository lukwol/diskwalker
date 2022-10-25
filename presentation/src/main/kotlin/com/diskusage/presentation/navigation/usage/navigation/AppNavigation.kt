package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import com.diskusage.libraries.navigation.LocalWindowController
import com.diskusage.libraries.navigation.NavHost
import com.diskusage.libraries.navigation.WindowsHost
import com.diskusage.libraries.viewmodel.navigation.composable
import com.diskusage.presentation.navigation.usage.greeter.GreeterScreen
import com.diskusage.presentation.navigation.usage.greeter.GreeterViewModel
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreen
import com.diskusage.presentation.navigation.usage.greeting.GreetingScreenViewModel

@Composable
fun AppNavigation() {
    WindowsHost(
        startWindow = AppWindows.GreetWindow
    ) {
        window(title = AppWindows.GreetWindow) {
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

        window(route = AppWindows.OtherWindow) {
            val windowsController = LocalWindowController.current

            Window(
                title = "Foooo",
                onCloseRequest = {
                    windowsController.close(AppWindows.OtherWindow)
                },
            ) {
                NavHost(
                    windowRoute = AppWindows.OtherWindow,
                    startRoute = AppRoutes.GreetingScreen,
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
        }
    }
}
