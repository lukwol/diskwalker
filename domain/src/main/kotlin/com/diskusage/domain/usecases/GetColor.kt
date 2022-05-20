@file:OptIn(ExperimentalGraphicsApi::class)

package com.diskusage.domain.usecases

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry
import java.lang.Float.min

class GetColor(
    private val getArc: GetArc,
    private val getRoot: GetRoot,
) {
    operator fun invoke(diskEntry: DiskEntry) = when (diskEntry) {
        is DiskEntry.File -> Color.hsl(
            hue = 0f,
            saturation = 0f,
            lightness = 0.35f
        )
        is DiskEntry.Directory -> {
            val arc = getArc.invoke(diskEntry, getRoot(diskEntry))
            Color.hsl(
                hue = min(arc.startAngle + arc.sweepAngle, 360f),
                saturation = (arc.startAngle / 360f + arc.sweepAngle / 360f) * 0.4f,
                lightness = 0.7f - (arc.depth / Constants.MaxChartDepth) * 0.4f
            )
        }
    }
}
