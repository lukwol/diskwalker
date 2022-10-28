package com.diskusage.libraries.viewmodel.screens.navigation.components

import androidx.compose.runtime.Composable
import com.diskusage.libraries.screens.navigation.ScreensNavigation
import com.diskusage.libraries.viewmodel.screens.navigation.data.TestRoutes
import com.diskusage.libraries.viewmodel.screens.navigation.screen

@Suppress("TestFunctionName")
@Composable
fun ScreenNavigation() {
    ScreensNavigation(
        startRoute = TestRoutes.FirstScreen
    ) {
        screen(
            route = TestRoutes.FirstScreen,
            viewModelFactory = { FirstScreenViewModel() }
        ) { viewModel ->
            FirstScreen(viewModel)
        }
    }
}
