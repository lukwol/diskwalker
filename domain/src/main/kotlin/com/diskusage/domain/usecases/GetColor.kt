package com.diskusage.domain.usecases

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry

class GetColor(
    private val getArc: GetArc,
    private val getRoot: GetRoot,
    private val includeDiskEntry: IncludeDiskEntry,
) {
    @OptIn(ExperimentalGraphicsApi::class)
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = when (diskEntry) {
        is DiskEntry.File -> Color.hsl(
            hue = 0f,
            saturation = 0f,
            lightness = 0.35f
        )
        is DiskEntry.Directory -> {
            with(getArc(diskEntry, fromDiskEntry)) {
                Color.hsl(
                    hue = (startAngle + sweepAngle).coerceIn(0f, 360f),
                    saturation = ((startAngle / 360f + sweepAngle / 360f) * 0.4f).coerceIn(0f, 1f),
                    lightness = (0.7f - (depth / Constants.MaxChartDepth) * 0.4f).coerceIn(0f, 1f),
                )
            }
        }
    }.run {
        takeIf { includeDiskEntry(diskEntry, fromDiskEntry) } ?: copy(alpha = 0f)
    }
}
