package com.diskusage.libraries.screens.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.libraries.screens.navigation.data.ButtonsTexts
import com.diskusage.libraries.screens.navigation.data.TestRoutes
import com.diskusage.libraries.screens.navigation.data.ThirdScreenArgs

@Suppress("TestFunctionName")
@Composable
fun ThirdScreen(args: ThirdScreenArgs) {
    val screensController = LocalScreensController.current

    Column {
        Text(args.toString())
        Button(
            onClick = { screensController.pop() }
        ) {
            Text(ButtonsTexts.PopScreen)
        }
        Button(
            onClick = { screensController.pop(upToRoute = TestRoutes.FirstScreen) }
        ) {
            Text(ButtonsTexts.PopToFirstScreen)
        }
    }
}
