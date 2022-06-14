package com.diskusage.domain.usecases.chart

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class IncludeDiskEntry(
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = checkSizeInRange(diskEntry, fromDiskEntry) && checkDepthInRange(diskEntry, fromDiskEntry)

    private fun checkSizeInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Boolean {
        val size = diskEntry.sizeOnDisk.toDouble() / fromDiskEntry.sizeOnDisk.toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= Constants.DiskEntrySizeFilterThreshold
    }

    private fun checkDepthInRange(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = getDepth(diskEntry, fromDiskEntry) <= Constants.MaxChartDepth
}
