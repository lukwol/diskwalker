package com.diskusage.libraries.windows.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalWindowController = compositionLocalOf<WindowsController> { WindowsControllerNoOp }
