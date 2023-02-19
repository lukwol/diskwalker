package com.diskusage.domain.usecases.chart

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.ChartData
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.usecases.chart.item.GetChartItem
import com.diskusage.domain.usecases.diskentry.GetDiskEntriesList
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.diskentry.SortDiskEntries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path

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

    suspend operator fun invoke(path: Path) = withContext(Dispatchers.Default) {
        getDiskEntriesList(path)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, path) }
            .map { chartItem ->
                hideNotIncluded(chartItem, path)
            }
            .let { startItems ->
                ChartData(
                    startItems = startItems,
                    endItems = null
                )
            }
    }

    suspend operator fun invoke(
        fromPath: Path,
        toPath: Path
    ) = withContext(Dispatchers.Default) {
        (getDiskEntriesList(fromPath) + getDiskEntriesList(toPath))
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, fromPath) to getChartItem(it, toPath) }
            .map { (startItem, endItem) ->
                hideNotIncluded(startItem, fromPath) to hideNotIncluded(endItem, toPath)
            }
            .unzip()
            .let { (startItems, endItems) ->
                ChartData(startItems, endItems)
            }
    }

    private fun hideNotIncluded(chartItem: ChartItem, fromPath: Path) = chartItem.apply {
        if (!includeDiskEntry(path, fromPath)) {
            color = color.copy(alpha = 0f)
        }
    }
}
