package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetDepth {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = depth(diskEntry, fromDiskEntry)

    private fun depth(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, counter: Float = 1f): Float = when {
        diskEntry.parent == null -> 0f
        diskEntry == fromDiskEntry -> counter
        else -> depth(diskEntry.parent!!, fromDiskEntry, counter)
    }
}
