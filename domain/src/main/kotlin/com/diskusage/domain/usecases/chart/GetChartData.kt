package com.diskusage.domain.usecases.chart

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.ChartData
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.item.GetChartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get a [GetChartData] with sorted [startItems][ChartData.startItems]
 * and sorted [endItems][ChartData.endItems].
 *
 * If `toDiskEntry` is not provided, [endItems][ChartData.endItems] is `null`.
 *
 * If [ChartItem] won't [be visible on chart][includeDiskEntry], set's it's [alpha][Color.alpha] value to **0f**.
 *
 * @see sortDiskEntries
 */
class GetChartData(
    private val getDiskEntriesList: GetDiskEntriesList,
    private val sortDiskEntries: SortDiskEntries,
    private val includeDiskEntry: IncludeDiskEntry,
    private val getChartItem: GetChartItem
) {
    suspend operator fun invoke(diskEntry: DiskEntry) = withContext(Dispatchers.Default) {
        getDiskEntriesList(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }
            .map { chartItem ->
                hideNotIncluded(chartItem, diskEntry)
            }
            .let { startItems ->
                ChartData(
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
            .map { (startItem, endItem) ->
                hideNotIncluded(startItem, fromDiskEntry) to hideNotIncluded(endItem, toDiskEntry)
            }
            .unzip()
            .let { (startItems, endItems) ->
                ChartData(startItems, endItems)
            }
    }

    private fun hideNotIncluded(chartItem: ChartItem, fromDiskEntry: DiskEntry) = chartItem.apply {
        if (!includeDiskEntry(diskEntry, fromDiskEntry)) {
            color = color.copy(alpha = 0f)
        }
    }
}
