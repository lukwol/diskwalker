package com.diskwalker.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.diskwalker.domain.common.Theme

private val LightPrimary = Color(0xFF386A1F)
private val LightOnPrimary = Color(0xFFFFFFFF)
private val LightPrimaryContainer = Color(0xFFB8F396)
private val LightOnPrimaryContainer = Color(0xFF082100)
private val LightSecondary = Color(0xFF55624C)
private val LightOnSecondary = Color(0xFFFFFFFF)
private val LightSecondaryContainer = Color(0xFFD9E7CB)
private val LightOnSecondaryContainer = Color(0xFF131F0D)
private val LightTertiary = Color(0xFF386666)
private val LightOnTertiary = Color(0xFFFFFFFF)
private val LightTertiaryContainer = Color(0xFFBBEBEB)
private val LightOnTertiaryContainer = Color(0xFF002020)
private val LightError = Color(0xFFBA1A1A)
private val LightErrorContainer = Color(0xFFFFDAD6)
private val LightOnError = Color(0xFFFFFFFF)
private val LightOnErrorContainer = Color(0xFF410002)
private val LightBackground = Color(0xFFFDFDF5)
private val LightOnBackground = Color(0xFF1A1C18)
private val LightOutline = Color(0xFF74796D)
private val LightInverseOnSurface = Color(0xFFF1F1EA)
private val LightInverseSurface = Color(0xFF2F312D)
private val LightInversePrimary = Color(0xFF9DD67D)
private val LightSurfaceTint = Color(0xFF386A1F)
private val LightOutlineVariant = Color(0xFFC3C8BB)
private val LightScrim = Color(0xFF000000)
private val LightSurface = Color(0xFFFAFAF3)
private val LightOnSurface = Color(0xFF1A1C18)
private val LightSurfaceVariant = Color(0xFFE0E4D6)
private val LightOnSurfaceVariant = Color(0xFF43483E)

private val DarkPrimary = Color(0xFF9DD67D)
private val DarkOnPrimary = Color(0xFF123800)
private val DarkPrimaryContainer = Color(0xFF215106)
private val DarkOnPrimaryContainer = Color(0xFFB8F396)
private val DarkSecondary = Color(0xFFBDCBB0)
private val DarkOnSecondary = Color(0xFF283420)
private val DarkSecondaryContainer = Color(0xFF3E4A35)
private val DarkOnSecondaryContainer = Color(0xFFD9E7CB)
private val DarkTertiary = Color(0xFFA0CFCF)
private val DarkOnTertiary = Color(0xFF003737)
private val DarkTertiaryContainer = Color(0xFF1E4E4E)
private val DarkOnTertiaryContainer = Color(0xFFBBEBEB)
private val DarkError = Color(0xFFFFB4AB)
private val DarkErrorContainer = Color(0xFF93000A)
private val DarkOnError = Color(0xFF690005)
private val DarkOnErrorContainer = Color(0xFFFFDAD6)
private val DarkBackground = Color(0xFF1A1C18)
private val DarkOnBackground = Color(0xFFE3E3DC)
private val DarkOutline = Color(0xFF8D9286)
private val DarkInverseOnSurface = Color(0xFF1A1C18)
private val DarkInverseSurface = Color(0xFFE3E3DC)
private val DarkInversePrimary = Color(0xFF386A1F)
private val DarkSurfaceTint = Color(0xFF9DD67D)
private val DarkOutlineVariant = Color(0xFF43483E)
private val DarkScrim = Color(0xFF000000)
private val DarkSurface = Color(0xFF121410)
private val DarkOnSurface = Color(0xFFC6C7C0)
private val DarkSurfaceVariant = Color(0xFF43483E)
private val DarkOnSurfaceVariant = Color(0xFFC3C8BB)

private val Seed = Color(0xFF8BC26C)

internal val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    errorContainer = LightErrorContainer,
    onError = LightOnError,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    outline = LightOutline,
    inverseOnSurface = LightInverseOnSurface,
    inverseSurface = LightInverseSurface,
    inversePrimary = LightInversePrimary,
    surfaceTint = LightSurfaceTint,
    outlineVariant = LightOutlineVariant,
    scrim = LightScrim,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
)

internal val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    errorContainer = DarkErrorContainer,
    onError = DarkOnError,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    outline = DarkOutline,
    inverseOnSurface = DarkInverseOnSurface,
    inverseSurface = DarkInverseSurface,
    inversePrimary = DarkInversePrimary,
    surfaceTint = DarkSurfaceTint,
    outlineVariant = DarkOutlineVariant,
    scrim = DarkScrim,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
)

internal fun Theme.colorScheme() = when (this) {
    Theme.Dark -> DarkColorScheme
    Theme.Light -> LightColorScheme
}
