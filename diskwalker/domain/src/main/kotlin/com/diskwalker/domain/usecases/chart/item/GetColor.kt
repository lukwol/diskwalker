package com.diskwalker.domain.usecases.chart.item

import androidx.compose.ui.graphics.Color
import com.diskwalker.domain.common.Constants.Chart.MaxArcsDepth
import com.diskwalker.domain.common.SystemTheme
import com.diskwalker.domain.common.Theme
import com.diskwalker.domain.model.chart.Arc
import com.diskwalker.domain.model.path.PathInfo
import com.diskwalker.domain.usecases.chart.item.arc.GetArc
import com.diskwalker.domain.usecases.chart.item.arc.GetStartAngle
import com.diskwalker.domain.usecases.chart.item.arc.GetSweepAngle
import com.diskwalker.domain.usecases.path.GetDepth
import com.diskwalker.domain.usecases.path.IsFile
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
        true -> fileColor()
        false -> {
            val angleEnd = precalculatedArc?.angleRange?.end
                ?: (getStartAngle(path, fromPath, disk) + getSweepAngle(path, fromPath, disk))

            val depth = getDepth(path, fromPath, disk)

            directoryColor(angleEnd, depth)
        }
    }

    private fun fileColor() = Color.hsl(
        hue = 0f,
        saturation = 0f,
        lightness = when (SystemTheme) {
            Theme.Dark -> 0.4f
            Theme.Light -> 0.7f
        },
    )

    private fun directoryColor(angleEnd: Float, depth: Int) = Color.hsl(
        hue = angleEnd,
        saturation = when (SystemTheme) {
            Theme.Dark -> ((angleEnd / 360f) * 0.4f)
            Theme.Light -> ((angleEnd / 360f) * 0.7f)
        },
        lightness = when (SystemTheme) {
            Theme.Dark -> (0.7f - (depth.toFloat() / MaxArcsDepth) * 0.2f)
            Theme.Light -> (0.7f + (depth.toFloat() / MaxArcsDepth) * 0.2f)
        },
    )
}
