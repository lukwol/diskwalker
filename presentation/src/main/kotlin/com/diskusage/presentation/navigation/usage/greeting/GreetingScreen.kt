package com.diskusage.presentation.navigation.usage.greeting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.libraries.navigation.LocalNavController
import com.diskusage.presentation.navigation.usage.navigation.AppRoutes

@Composable
fun GreetingScreen(
    viewModel: GreetingScreenViewModel
) {
    val navController = LocalNavController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.name.value = it }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.push(AppRoutes.SecondScreen, viewModel.name.value) }
        ) {
            Text("Greet")
        }
    }
}
