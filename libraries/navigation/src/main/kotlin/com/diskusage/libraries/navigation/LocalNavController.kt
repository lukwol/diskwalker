package com.diskusage.libraries.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided!")
}
