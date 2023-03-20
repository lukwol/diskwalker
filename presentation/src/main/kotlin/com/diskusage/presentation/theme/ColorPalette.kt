package com.diskusage.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val LightColors = lightColors(
    background = ColorPalette.Light.Azure,
    surface = ColorPalette.Light.Madang,
    primary = ColorPalette.Light.JungleMist,
    primaryVariant = ColorPalette.Light.Sail,
    secondary = ColorPalette.Light.JordyBlue,
    secondaryVariant = ColorPalette.Light.BilobaFlower,
    onBackground = ColorPalette.Light.DimGray,
    onPrimary = ColorPalette.Light.DimGray,
    onSecondary = ColorPalette.Light.DimGray,
    onSurface = ColorPalette.Light.DimGray,
    onError = ColorPalette.Light.DimGray,
)

val DarkColors = darkColors(
    background = ColorPalette.Dark.Onyx,
    surface = ColorPalette.Dark.Oxley,
    primary = ColorPalette.Dark.DarkSeaGreen,
    primaryVariant = ColorPalette.Dark.Menthol,
    secondary = ColorPalette.Dark.Ube,
    secondaryVariant = ColorPalette.Dark.BabyBlueEyes,
    onBackground = ColorPalette.Dark.Lotion,
    onPrimary = ColorPalette.Dark.Lotion,
    onSecondary = ColorPalette.Dark.Lotion,
    onSurface = ColorPalette.Dark.Lotion,
    onError = ColorPalette.Dark.Lotion,
)

object ColorPalette {
    object Dark {
        val Onyx = Color(0xFF363736)
        val Oxley = Color(0xFF73947C)
        val DarkSeaGreen = Color(0xFF81C784)
        val Menthol = Color(0xFFB2FAB4)
        val Lotion = Color(0xFFFAFAFA)
        val MiddleGreen = Color(0xFF519657)
        val Ube = Color(0xFF7986CB)
        val BabyBlueEyes = Color(0xFFAAB6FE)
        val Liberty = Color(0xFF49599A)
    }

    object Light {
        val Azure = Color(0xFFFCFDFD)
        val Madang = Color(0xFFB6D7A8)
        val JungleMist = Color(0xFFA2C4C9)
        val Sail = Color(0xFF9FC5E8)
        val DimGray = Color(0xFF666666)
        val Gossip = Color(0xFF93C47D)
        val JordyBlue = Color(0xFF79A8D4)
        val BilobaFlower = Color(0xFFB4A7D6)
        val TropicalBlue = Color(0xFFACCBE7)
    }
}
