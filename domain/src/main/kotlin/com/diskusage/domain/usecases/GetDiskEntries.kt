package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetDiskEntries(
    private val includeDiskEntry: IncludeDiskEntry,
) {
    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)
        .filter { includeDiskEntry(it, diskEntry) }

    private fun diskEntriesList(diskEntry: DiskEntry): List<DiskEntry> =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children.flatMap(::diskEntriesList)
            else -> listOf()
        }
}
