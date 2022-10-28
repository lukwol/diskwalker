package com.diskusage.libraries.screens.navigation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.libraries.screens.navigation.data.Arguments
import com.diskusage.libraries.screens.navigation.data.ControlsTexts
import com.diskusage.libraries.screens.navigation.data.TestRoutes

@Suppress("TestFunctionName")
@Composable
fun FirstScreen() {
    val screensController = LocalScreensController.current

    Button(
        onClick = { screensController.push(TestRoutes.SecondScreen, Arguments.SecondScreenArgs) }
    ) {
        Text(ControlsTexts.PushSecondScreenButtonText)
    }
}
