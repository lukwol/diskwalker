package com.diskwalker.domain.model.chart

import androidx.compose.ui.graphics.Color
import java.nio.file.Path

/**
 * Represents item drawn on chart
 *
 * @property path data for which an item is drawn
 * @property arc visual representation of [Path]
 * @property color [Color] of an [Arc]
 */
data class ChartItem(
    val path: Path,
    val arc: Arc,
    var color: Color,
)
