package com.diskusage.presentation.navigation.usage.greeter

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.libraries.navigation.screens.LocalScreensController
import com.diskusage.libraries.navigation.windows.LocalWindowController
import com.diskusage.presentation.navigation.usage.navigation.AppRoutes

@Composable
fun GreeterScreen(viewModel: GreeterViewModel) {
    val screensController = LocalScreensController.current
    val windowsController = LocalWindowController.current
    val name = viewModel.name

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Hello $name")

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                screensController.pop()
            }
        ) {
            Text("Go Back")
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (AppRoutes.FirstWindow in windowsController.routes) {
                    windowsController.open(AppRoutes.SecondWindow)
                } else {
                    windowsController.open(AppRoutes.FirstWindow)
                }
            }
        ) {
            Text("Other Window")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    GreeterScreen(GreeterViewModel("Adam"))
}
