package com.diskusage.domain.usecases.chart.chartitem

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
    private val getChartItem: GetChartItem
) {
    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getChartItem(diskEntry, fromDiskEntry),
            childItems = diskEntry.children
                .let(sortDiskEntries::invoke)
                .map { getChartItem(it, fromDiskEntry) }
        )
    }
}
