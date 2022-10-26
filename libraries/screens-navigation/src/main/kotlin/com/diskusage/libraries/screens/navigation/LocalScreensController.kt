package com.diskusage.libraries.screens.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalScreensController = compositionLocalOf<ScreensController> { ScreensControllerNoOp }
