package com.diskwalker.presentation.theme

import com.diskwalker.domain.common.Theme

fun Theme.colors() = when (this) {
    Theme.Dark -> DarkColors
    Theme.Light -> LightColors
}
