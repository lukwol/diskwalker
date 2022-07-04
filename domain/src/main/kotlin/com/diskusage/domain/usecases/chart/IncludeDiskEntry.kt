package com.diskusage.domain.usecases.chart

import com.diskusage.domain.common.Constants
import com.diskusage.domain.common.Constants.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.MaxSmallArcsDepth
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class IncludeDiskEntry(
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {

    /**
     * Checks whether given [diskEntry] should be included when drawing chart starting from [fromDiskEntry].
     *
     * To be included in the chart [diskEntry] can't be too small and too deeply nested.
     *
     * @param diskEntry [DiskEntry] for which inclusion will be checked
     * @param fromDiskEntry [DiskEntry] for which the chart will be drawn, uses root if not given
     *
     * @return answer if [diskEntry] is included in chart
     */
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = checkSizeInRange(diskEntry, fromDiskEntry) && checkDepthInRange(diskEntry, fromDiskEntry)

    /**
     * Check if size of [diskEntry] is not too small.
     * Items that are too small are not included in the chart.
     */
    private fun checkSizeInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Boolean {
        val size = diskEntry.sizeOnDisk.toDouble() / fromDiskEntry.sizeOnDisk.toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= Constants.MinChartItemAngle
    }

    /**
     * Check if [diskEntry] is not too deeply nested, starting from [fromDiskEntry].
     * Items that are too deeply nested are not included in the chart.
     */
    private fun checkDepthInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = getDepth(diskEntry, fromDiskEntry) <= MaxBigArcsDepth + MaxSmallArcsDepth
}
