package com.diskusage.domain.usecases.chart.chartitem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.common.Constants.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.MaxSmallArcsDepth
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetColor(
    private val getRoot: GetRoot,
    private val includeDiskEntry: IncludeDiskEntry,
    private val getDepth: GetDepth,
    private val getArc: GetArc,
) {
    /**
     * Get proper color for given [diskEntry] starting from [fromDiskEntry].
     *
     * When [diskEntry] is a [file][DiskEntry.File] color is always the same.
     *
     * When [diskEntry] is a [directory][DiskEntry.Directory] color is computed based on [arc][getArc] and [depth][getDepth].
     * Uses [precalculatedArc] is it's provided.
     *
     * If [diskEntry] won't [be visible on chart][includeDiskEntry], set's it's [alpha][Color.alpha] value to **0f**.
     *
     * @param diskEntry [DiskEntry] for which the [Color] is calculated
     * @param fromDiskEntry [DiskEntry] from which calculation will start, uses root if not given
     * @param precalculatedArc precalculated [diskEntry's][diskEntry] [Arc], invokes [getArc] if not given
     * @return [Color] in [HSL representation][Color.hsl]
     */
    @OptIn(ExperimentalGraphicsApi::class)
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
        precalculatedArc: Arc = getArc(diskEntry, fromDiskEntry),
    ): Color {
        val color = when (diskEntry) {
            is DiskEntry.File -> Color.hsl(
                hue = 0f,
                saturation = 0f,
                lightness = 0.35f
            )
            is DiskEntry.Directory -> with(precalculatedArc) {
                val depth = getDepth(diskEntry, fromDiskEntry)
                Color.hsl(
                    hue = (angleRange.end).coerceIn(0f, 360f),
                    saturation = ((angleRange.end / 360f) * 0.4f).coerceIn(0f, 1f),
                    lightness = (0.7f - (depth.toFloat() / (MaxBigArcsDepth + MaxSmallArcsDepth)) * 0.4f).coerceIn(0f, 1f),
                )
            }
        }
        return if (includeDiskEntry(diskEntry, fromDiskEntry)) {
            color
        } else {
            color.copy(alpha = 0f)
        }
    }
}
