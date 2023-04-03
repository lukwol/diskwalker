package com.diskwalker.domain.usecases.path

import com.diskwalker.domain.common.Constants.Chart.MaxArcsDepth
import com.diskwalker.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskwalker.domain.common.Constants.Chart.MaxSmallArcsDepth
import com.diskwalker.domain.common.Constants.Chart.MinChartItemAngle
import java.nio.file.Path

/**
 * Checks whether given `path` should be included when drawing the chart starting from `fromPath`.
 *
 * To be included in the chart `path` can't be too small and too deeply nested.

 * @see checkSizeInRange
 * @see checkDepthInRange
 */
class IncludePath(
    private val getDepth: GetDepth,
    private val getSizeOnDisk: GetSizeOnDisk,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
    ) = checkSizeInRange(path, fromPath) && checkDepthInRange(path, fromPath, disk)

    /**
     * Check if the size of [path] is not too small.
     * Items that are too small are not included in the chart.
     *
     * @see MinChartItemAngle
     */
    private fun checkSizeInRange(
        path: Path,
        fromPath: Path,
    ): Boolean {
        val size = getSizeOnDisk(path).toDouble() / getSizeOnDisk(fromPath).toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= MinChartItemAngle
    }

    /**
     * Check if [path] is not too deeply nested, starting from [fromPath].
     * Items that are too deeply nested are not included in the chart.
     *
     * @see MaxBigArcsDepth
     * @see MaxSmallArcsDepth
     */
    private fun checkDepthInRange(
        path: Path,
        fromPath: Path,
        disk: Path,
    ) = getDepth(path, fromPath, disk) <= MaxArcsDepth
}
