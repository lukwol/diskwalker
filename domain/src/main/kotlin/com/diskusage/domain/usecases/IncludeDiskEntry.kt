package com.diskusage.domain.usecases

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry

class IncludeDiskEntry(
    private val getDepth: GetDepth,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = validateSize(diskEntry, fromDiskEntry) &&
            validateDepth(diskEntry, fromDiskEntry)

    private fun validateSize(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Boolean {
        val size = diskEntry.size.toFloat() / fromDiskEntry.size.toFloat()
        return (size.takeIf { it.isFinite() } ?: 0f) >= Constants.DiskEntrySizeFilterThreshold
    }

    private fun validateDepth(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = getDepth(diskEntry, fromDiskEntry) <= Constants.MaxChartDepth
}
