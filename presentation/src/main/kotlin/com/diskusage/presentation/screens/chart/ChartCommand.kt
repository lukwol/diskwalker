package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry

sealed interface ChartCommand

data class OnChartPositionHovered(val position: Offset) : ChartCommand
data class OnChartPositionClicked(val position: Offset) : ChartCommand
data class OnHoverDiskEntry(val chartItem: ChartItem?) : ChartCommand
data class OnSelectDiskEntry(val diskEntry: DiskEntry) : ChartCommand
