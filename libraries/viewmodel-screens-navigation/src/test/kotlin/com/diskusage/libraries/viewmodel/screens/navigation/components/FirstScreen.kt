package com.diskusage.libraries.viewmodel.screens.navigation.components

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.diskusage.libraries.viewmodel.ViewModel

class FirstScreenViewModel : ViewModel() {
    var text = mutableStateOf("")
}

@Suppress("TestFunctionName")
@Composable
fun FirstScreen(viewModel: FirstScreenViewModel) {
    val (text, setText) = viewModel.text

    TextField(
        value = text,
        onValueChange = setText
    )
}
