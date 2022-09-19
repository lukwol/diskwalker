package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListData
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
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
    private val getChartItem: GetChartItem,
    private val includeDiskEntry: IncludeDiskEntry
) {

    // TODO: Should operate on ListItem(diskEntry, color).
    //  Create optimized usecase GetListItem that calculates color based on given ChartItem

    suspend operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getChartItem(diskEntry, fromDiskEntry),
            childItems = diskEntry.children
                .filter { includeDiskEntry(it, diskEntry) }
                .let(sortDiskEntries::invoke)
                .map { getChartItem(it, fromDiskEntry) }
        )
    }
}
