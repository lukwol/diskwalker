package com.diskusage.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = darkColors(
            background = ColorPalette.Onyx
        ),
        content = content
    )
}
