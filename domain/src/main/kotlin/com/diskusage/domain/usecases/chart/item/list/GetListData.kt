package com.diskusage.domain.usecases.chart.item.list

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListData
import com.diskusage.domain.usecases.chart.SortDiskEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get [ListData] with [selectedItem][ListData.parentItem]
 * and it's [childItems][ListData.childItems].
 *
 * [ChildItems][ListData.childItems] are sorted and have smaller items filtered out.
 *
 * @see sortDiskEntries
 */
class GetListData(
    private val sortDiskEntries: SortDiskEntries,
    private val getListItem: GetListItem,
) {
    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getListItem(diskEntry, fromDiskEntry),
            childItems = diskEntry.children
                .let(sortDiskEntries::invoke)
                .map { getListItem(it, fromDiskEntry) }
        )
    }
}
