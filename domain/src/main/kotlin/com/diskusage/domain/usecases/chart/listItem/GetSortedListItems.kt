package com.diskusage.domain.usecases.chart.listItem

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItem
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
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
    private val getListItem: GetListItem
) {
    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        diskEntry.children
            .filter { includeDiskEntry(it, diskEntry) }
            .let(sortDiskEntries::invoke)
            .map { getListItem(it, fromDiskEntry) }
    }

    // TODO: Should return ListItemsCollection
}
