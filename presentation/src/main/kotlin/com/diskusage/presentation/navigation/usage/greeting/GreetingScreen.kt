package com.diskusage.presentation.navigation.usage.greeting

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.presentation.navigation.usage.navigation.AppRoutes

@Composable
fun GreetingScreen(
    viewModel: GreetingScreenViewModel
) {
    var name by viewModel.name
    val screensController = LocalScreensController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { screensController.push(AppRoutes.GreeterScreen, name) }
        ) {
            Text("Greet")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    GreetingScreen(GreetingScreenViewModel())
}
