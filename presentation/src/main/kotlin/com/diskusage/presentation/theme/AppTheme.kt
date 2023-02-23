package com.diskusage.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = darkColors(
            background = ColorPalette.Onyx,
            surface = ColorPalette.Oxley,
            primary = ColorPalette.DarkSeaGreen,
            primaryVariant = ColorPalette.Menthol,
            secondary = ColorPalette.Ube,
            secondaryVariant = ColorPalette.BabyBlueEyes,
            onBackground = ColorPalette.Lotion,
            onPrimary = ColorPalette.Lotion,
            onSecondary = ColorPalette.Lotion,
            onSurface = ColorPalette.Lotion,
            onError = ColorPalette.Lotion
        ),
        typography = Typography(
            defaultFontFamily = InterFontFamily
        ),
        content = content,
    )
}
