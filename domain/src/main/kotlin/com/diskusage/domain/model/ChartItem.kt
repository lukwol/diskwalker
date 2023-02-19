package com.diskusage.domain.model

import androidx.compose.ui.graphics.Color
import java.nio.file.Path

/**
 * Represents item drawn on chart
 *
 * @property diskEntry data for which an item is drawn
 * @property arc visual representation of [DiskEntry]
 * @property color [Color] of an [Arc]
 */
data class ChartItem(
    val path: Path,
    val arc: Arc,
    var color: Color
)
