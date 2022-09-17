package com.diskusage.domain.usecases.chart.listItem

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItemsCollection
import com.diskusage.domain.usecases.chart.SortDiskEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get [ListItemsCollection] with [selectedItem][ListItemsCollection.parentItem]
 * and it's [childItems][ListItemsCollection.childItems].
 *
 * [ChildItems][ListItemsCollection.childItems] are sorted and have smaller items filtered out.
 *
 * @see sortDiskEntries
 */
class GetListItemsCollection(
    private val sortDiskEntries: SortDiskEntries,
    private val getListItem: GetListItem
) {
    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        ListItemsCollection(
            parentItem = getListItem(diskEntry, fromDiskEntry),
            childItems = diskEntry.children
                .let(sortDiskEntries::invoke)
                .map { getListItem(it, fromDiskEntry) }
        )
    }
}
