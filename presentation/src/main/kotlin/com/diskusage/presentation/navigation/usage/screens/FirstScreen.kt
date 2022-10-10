package com.diskusage.presentation.navigation.usage.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.presentation.navigation.LocalNavController
import com.diskusage.presentation.navigation.usage.navigation.AppRoutes

@Composable
fun FirstScreen() {
    val navController = LocalNavController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("FirstScreen")

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(AppRoutes.SecondScreen, "hello world") }
        ) {
            Text("Go to SecondScreen")
        }
    }
}
