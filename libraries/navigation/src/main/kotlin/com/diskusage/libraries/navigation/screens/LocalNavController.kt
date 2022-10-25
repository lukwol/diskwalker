package com.diskusage.libraries.navigation.screens

import androidx.compose.runtime.compositionLocalOf

val LocalNavController = compositionLocalOf<NavController> { NavControllerNoOp }
