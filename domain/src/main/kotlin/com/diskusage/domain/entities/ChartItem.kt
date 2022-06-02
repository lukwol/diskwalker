package com.diskusage.domain.entities

import androidx.compose.ui.graphics.Color


/**
 * Represents item drawn on chart
 *
 * @property diskEntry data for which an item is drawn
 * @property arc visual representation of [DiskEntry]
 * @property color [Color] of an [Arc]
 */
data class ChartItem(
    val diskEntry: DiskEntry,
    val arc: Arc,
    var color: Color,
)
