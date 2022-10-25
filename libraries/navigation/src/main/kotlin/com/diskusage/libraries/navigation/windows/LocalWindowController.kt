package com.diskusage.libraries.navigation.windows

import androidx.compose.runtime.compositionLocalOf

val LocalWindowController = compositionLocalOf<WindowsController> { WindowsControllerNoOp }
