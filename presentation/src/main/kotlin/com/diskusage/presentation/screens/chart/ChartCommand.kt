package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import java.nio.file.Path

sealed interface ChartCommand

data class OnChartPositionHovered(val position: Offset) : ChartCommand
data class OnChartPositionClicked(val position: Offset) : ChartCommand
data class OnSelectPath(val path: Path) : ChartCommand
