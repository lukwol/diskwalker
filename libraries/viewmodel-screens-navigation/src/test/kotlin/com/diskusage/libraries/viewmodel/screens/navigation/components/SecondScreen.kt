package com.diskusage.libraries.viewmodel.screens.navigation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.libraries.viewmodel.ViewModel
import com.diskusage.libraries.viewmodel.screens.navigation.data.ControlsTexts

class SecondScreenViewModel(initialText: String) : ViewModel() {
    var text = mutableStateOf(initialText)
}

@Suppress("TestFunctionName")
@Composable
fun SecondScreen(viewModel: SecondScreenViewModel) {
    val (text, setText) = viewModel.text

    val screensController = LocalScreensController.current

    TextField(
        value = text,
        onValueChange = setText
    )

    Button(
        onClick = { screensController.pop() }
    ) {
        Text(ControlsTexts.PopScreenButtonText)
    }
}
