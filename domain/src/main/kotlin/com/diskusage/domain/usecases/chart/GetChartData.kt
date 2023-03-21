package com.diskusage.domain.usecases.chart

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.chart.ChartData
import com.diskusage.domain.model.chart.ChartItem
import com.diskusage.domain.usecases.chart.item.GetChartItem
import com.diskusage.domain.usecases.path.GetPathsSet
import com.diskusage.domain.usecases.path.IncludePath
import com.diskusage.domain.usecases.path.SortPaths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.nio.file.Path

/**
 * Get a [GetChartData] with sorted [startItems][ChartData.startItems]
 * and sorted [endItems][ChartData.endItems].
 *
 * If `toPath` is not provided, [endItems][ChartData.endItems] is `null`.
 *
 * If [ChartItem] won't [be visible on chart][includePath], set's it's [alpha][Color.alpha] value to **0f**.
 *
 * @see sortPaths
 */
class GetChartData(
    private val getPathsSet: GetPathsSet,
    private val sortPaths: SortPaths,
    private val includePath: IncludePath,
    private val getChartItem: GetChartItem,
) {

    suspend operator fun invoke(
        path: Path,
        disk: Path,
    ) = withContext(Dispatchers.Default) {
        getPathsSet(path)
            .let(sortPaths::invoke)
            .map { async { getChartItem(it, path, disk) } }
            .awaitAll()
            .map { chartItem ->
                hideNotIncluded(chartItem, path)
            }
            .let { startItems ->
                ChartData(
                    startItems = startItems,
                    endItems = null,
                )
            }
    }

    suspend operator fun invoke(
        fromPath: Path,
        toPath: Path,
        disk: Path,
    ) = withContext(Dispatchers.Default) {
        (getPathsSet(fromPath) + getPathsSet(toPath))
            .let(sortPaths::invoke)
            .map { async { getChartItem(it, fromPath, disk) to getChartItem(it, toPath, disk) } }
            .awaitAll()
            .map { (startItem, endItem) ->
                hideNotIncluded(startItem, fromPath) to hideNotIncluded(endItem, toPath)
            }
            .unzip()
            .let { (startItems, endItems) ->
                ChartData(startItems, endItems)
            }
    }

    private fun hideNotIncluded(chartItem: ChartItem, fromPath: Path) = chartItem.apply {
        if (!includePath(path, fromPath)) {
            color = color.copy(alpha = 0f)
        }
    }
}
