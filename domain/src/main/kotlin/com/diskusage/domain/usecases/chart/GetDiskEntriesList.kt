package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry

class GetDiskEntriesList(
    private val includeDiskEntry: IncludeDiskEntry,
) {
    /**
     * Recursively traverses through all [children][DiskEntry.Directory.children] of given [diskEntry]
     * and collect them to single [List]
     *
     * @return [List] that contains [diskEntry] with all it's [descendants][DiskEntry.Relationship.Descendant]
     */
    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)

    private fun diskEntriesList(diskEntry: DiskEntry, fromDiskEntry: DiskEntry = diskEntry): List<DiskEntry> =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory ->
                diskEntry.children
                    .filter { includeDiskEntry(it, fromDiskEntry) }
                    .flatMap { diskEntriesList(it, fromDiskEntry) }
            is DiskEntry.File -> emptyList()
        }
}
