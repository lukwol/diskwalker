package com.diskusage.domain.usecases.chart.item

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.common.Constants
import com.diskusage.domain.common.Constants.Chart.MaxArcsDepth
import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.scan.PathInfo
import com.diskusage.domain.usecases.chart.item.arc.GetArc
import com.diskusage.domain.usecases.chart.item.arc.GetStartAngle
import com.diskusage.domain.usecases.chart.item.arc.GetSweepAngle
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot
import com.diskusage.domain.usecases.scan.GetPathInfo
import java.nio.file.Path

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
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getArc: GetArc,
    private val getPathInfo: GetPathInfo
) {

    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path),
        precalculatedArc: Arc? = null
    ) = when (getPathInfo(path)) {
        is PathInfo.File -> Constants.Chart.FileColor
        is PathInfo.Directory -> {
            val angleEnd = precalculatedArc?.angleRange?.end
                ?: (getStartAngle(path, fromPath) + getSweepAngle(path, fromPath))

            val depth = getDepth(path, fromPath)
            Color.hsl(
                hue = angleEnd,
                saturation = ((angleEnd / 360f) * 0.4f).coerceIn(0f, 0.4f),
                lightness = (0.7f - (depth.toFloat() / MaxArcsDepth) * 0.4f).coerceIn(0.3f, 0.7f)
            )
        }
    }
}
