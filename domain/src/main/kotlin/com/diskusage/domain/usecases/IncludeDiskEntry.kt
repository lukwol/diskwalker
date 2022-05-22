package com.diskusage.domain.usecases

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry

class IncludeDiskEntry(
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = validateSize(diskEntry, fromDiskEntry) &&
        validateDepth(diskEntry, fromDiskEntry)

    private fun validateSize(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Boolean {
        val size = diskEntry.size.toDouble() / fromDiskEntry.size.toDouble()
        return (size.takeIf(Double::isFinite)?.toFloat() ?: 0f) >= Constants.DiskEntrySizeFilterThreshold
    }

    private fun validateDepth(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = getDepth(diskEntry, fromDiskEntry) <= Constants.MaxChartDepth
}
