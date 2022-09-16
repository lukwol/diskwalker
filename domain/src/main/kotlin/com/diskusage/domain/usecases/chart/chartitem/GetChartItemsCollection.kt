package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.model.ChartItemsCollection
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.SortDiskEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get a [GetChartItemsCollection] with sorted [startItems][ChartItemsCollection.startItems]
 * and sorted [endItems][ChartItemsCollection.endItems].
 *
 * If `toDiskEntry` is not provided, [endItems][ChartItemsCollection.endItems] is `null`.
 *
 * @see sortDiskEntries
 */
class GetChartItemsCollection(
    private val getDiskEntriesList: GetDiskEntriesList,
    private val sortDiskEntries: SortDiskEntries,
    private val getChartItem: GetChartItem
) {
    suspend operator fun invoke(diskEntry: DiskEntry) = withContext(Dispatchers.Default) {
        getDiskEntriesList(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }
            .let { startItems ->
                ChartItemsCollection(
                    startItems = startItems,
                    endItems = null
                )
            }
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
            .let { (startItems, endItems) ->
                ChartItemsCollection(startItems, endItems)
            }
    }
}
