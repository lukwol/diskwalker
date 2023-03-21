package com.diskusage.domain.usecases.chart.item

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.common.Constants
import com.diskusage.domain.common.Constants.Chart.MaxArcsDepth
import com.diskusage.domain.model.chart.Arc
import com.diskusage.domain.model.path.PathInfo
import com.diskusage.domain.usecases.chart.item.arc.GetArc
import com.diskusage.domain.usecases.chart.item.arc.GetStartAngle
import com.diskusage.domain.usecases.chart.item.arc.GetSweepAngle
import com.diskusage.domain.usecases.path.GetDepth
import com.diskusage.domain.usecases.path.IsFile
import java.nio.file.Path

/**
 * Get proper [Color] in [HSL representation][Color.hsl] for given `path` starting from `fromPath`.
 *
 * When `path` is a [file][PathInfo.File] color is always the same.
 *
 * When `path` is a [directory][PathInfo.Directory] color is computed based on [arc][getArc] and [depth][getDepth].
 * Uses `precalculatedArc` is it's provided.
 */
class GetColor(
    private val getDepth: GetDepth,
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getArc: GetArc,
    private val isFile: IsFile,
) {

    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
        precalculatedArc: Arc? = null,
    ) = when (isFile(path)) {
        true -> Constants.Chart.FileColor
        false -> {
            val angleEnd = precalculatedArc?.angleRange?.end
                ?: (getStartAngle(path, fromPath, disk) + getSweepAngle(path, fromPath))

            val depth = getDepth(path, fromPath)
            Color.hsl(
                hue = angleEnd,
                saturation = ((angleEnd / 360f) * 0.4f).coerceIn(0f, 0.4f),
                lightness = (0.7f - (depth.toFloat() / MaxArcsDepth) * 0.4f).coerceIn(0.3f, 0.7f),
            )
        }
    }
}
