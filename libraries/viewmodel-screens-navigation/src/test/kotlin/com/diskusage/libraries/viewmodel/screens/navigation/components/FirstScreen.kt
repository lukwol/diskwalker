package com.diskusage.libraries.viewmodel.screens.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.libraries.viewmodel.ViewModel
import com.diskusage.libraries.viewmodel.screens.navigation.data.ControlsTexts
import com.diskusage.libraries.viewmodel.screens.navigation.data.TestRoutes

class FirstScreenViewModel : ViewModel() {
    var text = mutableStateOf("")
}

@Suppress("TestFunctionName")
@Composable
fun FirstScreen(viewModel: FirstScreenViewModel) {
    val (text, setText) = viewModel.text

    val screensController = LocalScreensController.current

    Column {
        TextField(
            value = text,
            onValueChange = setText
        )

        Button(
            onClick = { screensController.push(TestRoutes.SecondScreen, text) }
        ) {
            Text(ControlsTexts.PushSecondScreenButtonText)
        }
    }
}
