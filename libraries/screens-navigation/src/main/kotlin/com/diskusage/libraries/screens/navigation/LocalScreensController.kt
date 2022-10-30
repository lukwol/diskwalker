package com.diskusage.libraries.screens.navigation

import androidx.compose.runtime.compositionLocalOf

/**
 *  [CompositionLocal][androidx.compose.runtime.CompositionLocal] of [ScreensController],
 *  defaults to [ScreensControllerNoOp].
 */
val LocalScreensController = compositionLocalOf<ScreensController> { ScreensControllerNoOp }
