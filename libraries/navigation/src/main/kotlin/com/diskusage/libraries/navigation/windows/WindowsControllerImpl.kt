package com.diskusage.libraries.navigation.windows

import androidx.compose.runtime.mutableStateOf

interface WindowsController {
    val windows: Set<String>
    fun open(window: String)
    fun close(window: String)
}

class WindowsControllerImpl(startWindow: String) : WindowsController {
    internal val windowsState = mutableStateOf(setOf(startWindow))

    override val windows get() = windowsState.value

    override fun open(window: String) {
        windowsState.value += window
    }

    override fun close(window: String) {
        windowsState.value -= window
    }
}

internal object WindowsControllerNoOp : WindowsController {
    override val windows: Set<String> = emptySet()

    override fun open(window: String) = Unit

    override fun close(window: String) = Unit
}
