package com.diskusage.domain.usecases.chart

import com.diskusage.domain.model.DiskEntry

/**
 * Recursively traverses through all [children][DiskEntry.children] of given `diskEntry`
 * and collect them to single [List]
 */
class GetDiskEntriesList(
    private val includeDiskEntry: IncludeDiskEntry
) {
    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)

    private fun diskEntriesList(diskEntry: DiskEntry, fromDiskEntry: DiskEntry = diskEntry): List<DiskEntry> =
        listOf(diskEntry) + diskEntry.children
            .filter { includeDiskEntry(it, fromDiskEntry) }
            .flatMap { diskEntriesList(it, fromDiskEntry) }
}
