package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry

class GetDiskEntries(
    private val includeDiskEntry: IncludeDiskEntry,
) {
    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)

    private fun diskEntriesList(diskEntry: DiskEntry, fromDiskEntry: DiskEntry = diskEntry): List<DiskEntry> =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children
                .filter { includeDiskEntry(it, fromDiskEntry) }
                .flatMap { diskEntriesList(it, fromDiskEntry) }
            else -> listOf()
        }
}
