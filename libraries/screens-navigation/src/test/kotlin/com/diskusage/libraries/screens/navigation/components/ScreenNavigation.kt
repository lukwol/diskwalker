package com.diskusage.libraries.screens.navigation.components

import androidx.compose.runtime.Composable
import com.diskusage.libraries.screens.navigation.ScreensNavigation
import com.diskusage.libraries.screens.navigation.data.TestRoutes
import com.diskusage.libraries.screens.navigation.data.ThirdScreenArgs

@Suppress("TestFunctionName")
@Composable
fun ScreenNavigation() {
    ScreensNavigation(
        startRoute = TestRoutes.FirstScreen
    ) {
        screen(TestRoutes.FirstScreen) {
            FirstScreen()
        }

        screen(TestRoutes.SecondScreen) { args ->
            SecondScreen(args as String)
        }

        screen(TestRoutes.ThirdScreen) { args ->
            ThirdScreen(args as ThirdScreenArgs)
        }
    }
}
