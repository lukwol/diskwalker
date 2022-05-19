package com.diskusage.domain.usecases

import com.diskusage.domain.common.DiskEntrySizeFilterThreshold
import com.diskusage.domain.entities.DiskEntry

class GetDiskEntries {
    operator fun invoke(diskEntry: DiskEntry) = diskEntries(diskEntry)
        .filter { (diskEntry.size / it.size) < DiskEntrySizeFilterThreshold }

    private fun diskEntries(diskEntry: DiskEntry): List<DiskEntry> =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children.flatMap(::diskEntries)
            else -> listOf()
        }
}
