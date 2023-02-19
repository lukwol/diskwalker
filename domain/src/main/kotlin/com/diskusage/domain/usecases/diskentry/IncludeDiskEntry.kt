package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.common.Constants.Chart.MaxArcsDepth
import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.MaxSmallArcsDepth
import com.diskusage.domain.common.Constants.Chart.MinChartItemAngle
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.scan.GetSizeOnDisk
import java.nio.file.Path

/**
 * Checks whether given `diskEntry` should be included when drawing the chart starting from `fromDiskEntry`.
 *
 * To be included in the chart diskEntry`, `diskEntry` can't be too small and too deeply nested.

 * @see checkSizeInRange
 * @see checkDepthInRange
 */
class IncludeDiskEntry(
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
    private val getSizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ) = checkSizeInRange(diskEntry, fromDiskEntry) && checkDepthInRange(diskEntry, fromDiskEntry)

    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ) = checkSizeInRange(path, fromPath) && checkDepthInRange(path, fromPath)

    /**
     * Check if the size of [diskEntry] is not too small.
     * Items that are too small are not included in the chart.
     *
     * @see MinChartItemAngle
     */
    private fun checkSizeInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry
    ): Boolean {
        val size = diskEntry.sizeOnDisk.toDouble() / fromDiskEntry.sizeOnDisk.toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= MinChartItemAngle
    }

    private fun checkSizeInRange(
        path: Path,
        fromPath: Path
    ): Boolean {
        val size = getSizeOnDisk(path).toDouble() / getSizeOnDisk(fromPath).toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= MinChartItemAngle
    }

    /**
     * Check if [diskEntry] is not too deeply nested, starting from [fromDiskEntry].
     * Items that are too deeply nested are not included in the chart.
     *
     * @see MaxBigArcsDepth
     * @see MaxSmallArcsDepth
     */
    private fun checkDepthInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry
    ) = getDepth(diskEntry, fromDiskEntry) <= MaxArcsDepth

    private fun checkDepthInRange(
        path: Path,
        fromPath: Path
    ) = getDepth(path, fromPath) <= MaxArcsDepth
}
