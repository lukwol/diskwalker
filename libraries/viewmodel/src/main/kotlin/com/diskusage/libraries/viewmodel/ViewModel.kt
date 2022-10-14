package com.diskusage.libraries.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class ViewModel {
    val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}
