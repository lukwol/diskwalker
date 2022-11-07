package com.diskusage.presentation.navigation.usage.greeting

import androidx.compose.runtime.mutableStateOf
import io.github.lukwol.viewmodel.ViewModel

class GreetingScreenViewModel : ViewModel() {
    var name = mutableStateOf("")
}
