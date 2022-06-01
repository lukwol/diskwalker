package com.diskusage.domain.entities

import androidx.compose.ui.graphics.Color

data class ChartItem(
    val diskEntry: DiskEntry,
    val arc: Arc,
    var color: Color,
)
