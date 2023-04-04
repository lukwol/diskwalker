package com.diskwalker.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val LightColors = lightColors(
    background = ColorPalette.Light.Porcelain,
    surface = ColorPalette.Light.Zanah,
    primary = ColorPalette.Light.TeaGreen,
    primaryVariant = ColorPalette.Light.Cornflower,
    secondary = ColorPalette.Light.Spindle,
    secondaryVariant = ColorPalette.Light.Wistful,
    error = ColorPalette.Light.VividTangerine,
    onBackground = ColorPalette.Light.DoveGray,
    onPrimary = ColorPalette.Light.DoveGray,
    onSecondary = ColorPalette.Light.DoveGray,
    onSurface = ColorPalette.Light.DoveGray,
    onError = ColorPalette.Light.DoveGray,
)

val DarkColors = darkColors(
    background = ColorPalette.Dark.LunarGreen,
    surface = ColorPalette.Dark.Laurel,
    primary = ColorPalette.Dark.DeYork,
    primaryVariant = ColorPalette.Dark.Gossip,
    secondary = ColorPalette.Dark.MoodyBlue,
    secondaryVariant = ColorPalette.Dark.ShipCove,
    error = ColorPalette.Dark.Cinnabar,
    onBackground = ColorPalette.Dark.Alabaster,
    onPrimary = ColorPalette.Dark.Alabaster,
    onSecondary = ColorPalette.Dark.Alabaster,
    onSurface = ColorPalette.Dark.Alabaster,
    onError = ColorPalette.Dark.Alabaster,
)

object ColorPalette {
    object Dark {
        val LunarGreen = Color(0xFF363736)
        val Laurel = Color(0xFF73947C)
        val DeYork = Color(0xFF81C784)
        val Gossip = Color(0xFF71A473)
        val Alabaster = Color(0xFFFAFAFA)
        val FruitSalad = Color(0xFF519657)
        val MoodyBlue = Color(0xFF7682C0)
        val ShipCove = Color(0xFF848DC5)
        val KashmirBlue = Color(0xFF49599A)
        val Cinnabar = Color(0xFFDD6E6E)
    }

    object Light {
        val Porcelain = Color(0xFFFCFDFD)
        val Zanah = Color(0xFFCFE5C5)
        val TeaGreen = Color(0xFFCEF5BD)
        val Cornflower = Color(0xFF9FC5E8)
        val DoveGray = Color(0xFF545454)
        val Olivine = Color(0xFF93C47D)
        val Spindle = Color(0xFFC5C4EF)
        val Wistful = Color(0xFFB4A7D6)
        val RegentStBlue = Color(0xFFACDBE7)
        val VividTangerine = Color(0xFFFF8B8B)
    }
}
