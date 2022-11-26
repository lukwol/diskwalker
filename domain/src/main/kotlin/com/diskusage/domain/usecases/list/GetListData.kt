package com.diskusage.domain.usecases.list

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListData
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.diskentry.SortDiskEntries
import com.diskusage.domain.usecases.list.item.GetListItem
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
    private val includeDiskEntry: IncludeDiskEntry
) {
    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getListItem(diskEntry, fromDiskEntry),
            childItems = diskEntry.children
                .filter { includeDiskEntry(it, diskEntry) }
                .let(sortDiskEntries::invoke)
                .map { getListItem(it, fromDiskEntry) }
        )
    }
}
