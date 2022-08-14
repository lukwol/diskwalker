package com.diskusage.domain.usecases.chart.listItem

import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.entities.ListItem
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.GetColor
import com.diskusage.domain.usecases.diskentry.GetRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get a sorted list of [items][ListItem] required for drawing the entries list.
 *
 * @see sortDiskEntries
 */
class GetSortedListItems(
    private val sortDiskEntries: SortDiskEntries,
    private val includeDiskEntry: IncludeDiskEntry,
    private val getRoot: GetRoot,
    private val getColor: GetColor
) {
    suspend operator fun invoke(diskEntry: DiskEntry) = withContext(Dispatchers.Default) {
        getChildDiskEntries(diskEntry)
            .filter { includeDiskEntry(it, diskEntry) }
            .let(sortDiskEntries::invoke)
            .map { getListItem(it, diskEntry) }
    }

    private fun getChildDiskEntries(diskEntry: DiskEntry) =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children
            is DiskEntry.File -> emptyList()
        }

    private fun getListItem(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ) = ListItem(
        diskEntry = diskEntry,
        color = getColor(diskEntry, fromDiskEntry)
    )
}
