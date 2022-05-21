package com.diskusage.domain.usecases

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry
import java.lang.Float.min

class GetColor(
    private val getArc: GetArc,
    private val includeDiskEntry: IncludeDiskEntry,
) {
    @OptIn(ExperimentalGraphicsApi::class)
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry.root,
    ) = when (diskEntry) {
        is DiskEntry.File -> Color.hsl(
            hue = 0f,
            saturation = 0f,
            lightness = 0.35f
        )
        is DiskEntry.Directory -> {
            with(getArc.invoke(diskEntry)) {
                Color.hsl(
                    hue = min(startAngle + sweepAngle, 360f),
                    saturation = (startAngle / 360f + sweepAngle / 360f) * 0.4f,
                    lightness = 0.7f - (depth / Constants.MaxChartDepth) * 0.4f
                )
            }
        }
    }.run {
        if (includeDiskEntry(diskEntry, fromDiskEntry)) this else copy(alpha = 0f)
    }
}
