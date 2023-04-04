package com.diskwalker.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import com.diskwalker.domain.common.SystemTheme

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = SystemTheme.colors(),
        typography = Typography(
            defaultFontFamily = InterFontFamily,
        ),
        content = content,
    )
}
