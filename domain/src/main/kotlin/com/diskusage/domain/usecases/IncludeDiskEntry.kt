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
    ) = diskEntry.size.toFloat() / fromDiskEntry.size.toFloat() >= Constants.DiskEntrySizeFilterThreshold

    private fun validateDepth(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = getDepth(diskEntry, fromDiskEntry) <= Constants.MaxChartDepth
}
