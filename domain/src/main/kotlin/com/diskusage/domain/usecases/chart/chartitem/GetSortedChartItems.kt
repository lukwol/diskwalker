package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.SortDiskEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get a sorted [chart items][ChartItem] list required for drawing the chart.
 *
 * Can return a pair of lists for chart to be able to animate from one to another.
 *
 * @see sortDiskEntries
 */
class GetSortedChartItems(
    private val getDiskEntriesList: GetDiskEntriesList,
    private val sortDiskEntries: SortDiskEntries,
    private val getChartItem: GetChartItem
) {
    suspend operator fun invoke(diskEntry: DiskEntry) = withContext(Dispatchers.Default) {
        getDiskEntriesList(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }
    }

    suspend operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry
    ) = withContext(Dispatchers.Default) {
        (getDiskEntriesList(fromDiskEntry) + getDiskEntriesList(toDiskEntry))
            .distinctBy(DiskEntry::path)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
            .unzip()
    }

    // TODO: Should return ListItemsCollection
}
