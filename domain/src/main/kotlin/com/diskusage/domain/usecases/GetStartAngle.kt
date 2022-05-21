package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetStartAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = calculateSize(diskEntry, fromDiskEntry).toFloat() / fromDiskEntry.size.toFloat()

    private fun calculateSize(diskEntry: DiskEntry, fromDiskEntry: DiskEntry, size: Long = 0): Long = when {
        diskEntry.parent == null -> 0L
        diskEntry == fromDiskEntry -> 0L
        diskEntry.parent == fromDiskEntry -> size
        else -> calculateSize(diskEntry.parent!!, fromDiskEntry, size + largerSiblingsSize(diskEntry))
    }

    private fun largerSiblingsSize(diskEntry: DiskEntry) = diskEntry
        .parent
        ?.children
        ?.takeLastWhile { it.size > diskEntry.size }
        ?.map(DiskEntry::size)
        ?.sum() ?: 0
}
