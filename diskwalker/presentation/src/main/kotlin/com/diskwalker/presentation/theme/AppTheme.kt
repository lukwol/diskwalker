package com.diskwalker.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.diskwalker.domain.common.SystemTheme

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = SystemTheme.colorScheme(),
        content = content,
    )
}
