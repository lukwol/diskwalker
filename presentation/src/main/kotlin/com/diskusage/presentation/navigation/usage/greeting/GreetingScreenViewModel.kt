package com.diskusage.presentation.navigation.usage.greeting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.diskusage.libraries.viewmodel.ViewModel

class GreetingScreenViewModel(
    val name: MutableState<String> = mutableStateOf("")
) : ViewModel()
