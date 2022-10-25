package com.diskusage.libraries.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavController = compositionLocalOf<NavController> { NavControllerNoOp }
val LocalWindowController = compositionLocalOf {
    // TODO: Provide WindowsControllerNoOp
    WindowsController("")
}
