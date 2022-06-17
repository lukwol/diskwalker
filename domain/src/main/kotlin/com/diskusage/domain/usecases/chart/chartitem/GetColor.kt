package com.diskusage.domain.usecases.chart.chartitem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetColor(
    private val getRoot: GetRoot,
    private val includeDiskEntry: IncludeDiskEntry,
    private val getDepth: GetDepth,
) {
    @OptIn(ExperimentalGraphicsApi::class)
    operator fun invoke(
        precalculatedArc: Arc,
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = when (diskEntry) {
        is DiskEntry.File -> Color.hsl(
            hue = 0f,
            saturation = 0f,
            lightness = 0.35f
        )
        is DiskEntry.Directory -> with(precalculatedArc) {
            Color.hsl(
                hue = (angleRange.endInclusive).coerceIn(0f, 360f),
                saturation = ((angleRange.endInclusive / 360f) * 0.4f).coerceIn(0f, 1f),
                lightness = (0.7f - (getDepth(diskEntry, fromDiskEntry).toFloat() / Constants.MaxChartDepth) * 0.4f).coerceIn(0f, 1f),
            )
        }
    }.run {
        takeIf { includeDiskEntry(diskEntry, fromDiskEntry) } ?: copy(alpha = 0f)
    }
}
