package com.diskusage.domain.usecases.chart.item.chart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants.Chart.MaxArcsDepth
import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.item.chart.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

/**
 * Get proper [Color] in [HSL representation][Color.hsl] for given `diskEntry` starting from `fromDiskEntry`.
 *
 * When `diskEntry` is a [file][DiskEntry.Type.File] color is always the same.
 *
 * When `diskEntry` is a [directory][DiskEntry.Type.Directory] color is computed based on [arc][getArc] and [depth][getDepth].
 * Uses `precalculatedArc` is it's provided.
 */
class GetColor(
    private val getRoot: GetRoot,
    private val getDepth: GetDepth,
    private val getArc: GetArc
) {
    @OptIn(ExperimentalGraphicsApi::class)
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
        precalculatedArc: Arc = getArc(diskEntry, fromDiskEntry)
    ) = when (diskEntry.type) {
        DiskEntry.Type.File -> Color.hsl(
            hue = 0f,
            saturation = 0f,
            lightness = 0.35f
        )

        DiskEntry.Type.Directory -> with(precalculatedArc) {
            val depth = getDepth(diskEntry, fromDiskEntry)
            Color.hsl(
                hue = (angleRange.end).coerceIn(0f, 360f),
                saturation = ((angleRange.end / 360f) * 0.4f).coerceIn(0f, 0.4f),
                lightness = (0.7f - (depth.toFloat() / MaxArcsDepth) * 0.4f).coerceIn(0.3f, 0.7f)
            )
        }
    }
}
