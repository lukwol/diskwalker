package com.diskwalker.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskwalker.domain.model.chart.ChartItem
import com.diskwalker.domain.usecases.chart.GetChartData
import com.diskwalker.domain.usecases.chart.item.arc.IsArcSelected
import com.diskwalker.domain.usecases.disk.GetDiskInfo
import com.diskwalker.domain.usecases.list.GetListData
import com.diskwalker.domain.usecases.path.GetPathInfo
import com.diskwalker.domain.usecases.path.IncludePath
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.nio.file.Path

class ChartViewModel(
    path: Path,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includePath: IncludePath,
    private val isArcSelected: IsArcSelected,
    private val getPathInfo: GetPathInfo,
    private val getDiskInfo: GetDiskInfo,
) : ViewModel(),
    AsyncState<ChartViewState> by AsyncState.Delegate(
        ChartViewState(
            disk = path,
            path = path,
            diskInfo = getDiskInfo(path),
            pathInfo = getPathInfo(path),
        ),
    ) {

    private var listDataJob: Job? = null

    init {
        withState { state ->
            coroutineScope.launch {
                val listData = async { getListData(path, state.disk) }
                val chartData = async { getChartData(path, state.disk) }
                (listData.await() to chartData.await())
                    .let { (listData, chartData) ->
                        setState {
                            copy(
                                listData = listData,
                                chartData = chartData,
                            )
                        }
                    }
            }
        }
    }

    fun onCommand(command: ChartCommand) = with(command) {
        when (this) {
            is OnChartPositionClicked -> onChartPositionClicked(position)
            is OnChartPositionHovered -> onChartPositionHovered(position)
            is OnSelectPath -> onSelectPath(path)
        }
    }

    private fun onChartPositionHovered(position: Offset) = withState { state ->
        if (state.chartData != null) {
            val (startItems, endItems) = state.chartData
            (endItems ?: startItems)
                .filter { includePath(it.path, state.path, state.disk) }
                .find { isArcSelected(it.arc, position) }
                .let(::onHoverChartItem)
        }
    }

    private fun onChartPositionClicked(position: Offset) = withState { state ->
        if (state.chartData != null) {
            val (startItems, endItems) = state.chartData
            (endItems ?: startItems)
                .filter { includePath(it.path, state.path, state.disk) }
                .find { isArcSelected(it.arc, position) }
                ?.let(ChartItem::path)
                ?.let(::onSelectPath)
        }
    }

    private fun onHoverChartItem(chartItem: ChartItem?) = withState { state ->
        val selectedPath = chartItem?.path ?: state.path
        val previousPath = state.path
        if (selectedPath != state.listData?.parentItem?.path) {
            listDataJob?.cancel()
            listDataJob = coroutineScope.launch {
                val listData = getListData(
                    path = selectedPath,
                    fromPath = previousPath,
                    disk = state.disk,
                )
                setState {
                    copy(listData = listData)
                }
            }
        }
    }

    private fun onSelectPath(path: Path) = withState { state ->
        val pathInfo = getPathInfo(path)
        val previousPath = state.path
        val selectedPath = when {
            pathInfo.isFile -> null
            pathInfo.sizeOnDisk == 0L -> null
            path == state.disk -> null
            path == previousPath -> path.parent
            else -> path
        }

        if (selectedPath != null) {
            coroutineScope.launch {
                val listData = async {
                    getListData(
                        path = selectedPath,
                        fromPath = selectedPath,
                        disk = state.disk,
                    )
                }
                val chartData = async {
                    getChartData(
                        fromPath = previousPath,
                        toPath = selectedPath,
                        disk = state.disk,
                    )
                }
                (listData.await() to chartData.await())
                    .let { (listData, chartData) ->
                        setState {
                            copy(
                                path = selectedPath,
                                listData = listData,
                                chartData = chartData,
                            )
                        }
                    }
            }
        }
    }
}
