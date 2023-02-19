package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.scan.ScanItem
import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.arc.IsArcSelected
import com.diskusage.domain.usecases.disk.GetDiskInfo
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.list.GetListData
import com.diskusage.domain.usecases.scan.GetScanItem
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.nio.file.Path

class ChartViewModel(
    path: Path,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected,
    private val getScanItem: GetScanItem,
    private val getDiskInfo: GetDiskInfo
) : ViewModel(),
    AsyncState<ChartViewState> by AsyncState.Delegate(
        ChartViewState(
            path = path,
            diskInfo = getDiskInfo(path),
            scanItem = getScanItem(path)
        )
    ) {

    init {
        viewModelScope.launch {
            val listData = async { getListData(path) }
            val chartData = async { getChartData(path) }
            (listData.await() to chartData.await())
                .let { (listData, chartData) ->
                    setState {
                        copy(
                            listData = listData,
                            chartData = chartData
                        )
                    }
                }
        }
    }

    fun onCommand(command: ChartCommand) = with(command) {
        when (this) {
            is OnChartPositionClicked -> onChartPositionClicked(position)
            is OnChartPositionHovered -> onChartPositionHovered(position)
            is OnHoverDiskEntry -> onHoverDiskEntry(chartItem)
            is OnSelectDiskEntry -> onSelectDiskEntry(path)
        }
    }

    private fun onChartPositionHovered(position: Offset) = withState { state ->
        if (state.chartData != null) {
            val (startItems, endItems) = state.chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.path, state.path) }
                .find { isArcSelected(it.arc, position) }
                .let(::onHoverDiskEntry)
        }
    }

    private fun onChartPositionClicked(position: Offset) = withState { state ->
        if (state.chartData != null) {
            val (startItems, endItems) = state.chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.path, state.path) }
                .find { isArcSelected(it.arc, position) }
                ?.let(ChartItem::path)
                ?.let(::onSelectDiskEntry)
        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) = withState { state ->
        val selectedPath = chartItem?.path ?: state.path
        if (selectedPath != state.listData?.parentItem?.path) {
            viewModelScope.launch {
                withTimeoutOrNull(Constants.HeavyOperationsTimeout) {
                    val listData = getListData(
                        path = selectedPath,
                        fromPath = state.path
                    )
                    setState {
                        copy(listData = listData)
                    }
                }
            }
        }
    }

    private fun onSelectDiskEntry(path: Path) = withState { state ->
        val scanItem = getScanItem(path)
        val previousPath = state.path
        val selectedPath = when {
            scanItem is ScanItem.File -> null
            scanItem.sizeOnDisk == 0L -> null
            path == previousPath -> path.parent
            else -> path
        }

        if (selectedPath != null) {
            viewModelScope.launch {
                val listData = async {
                    getListData(selectedPath)
                }
                val chartData = async {
                    getChartData(
                        fromPath = previousPath,
                        toPath = selectedPath
                    )
                }
                (listData.await() to chartData.await())
                    .let { (listData, chartData) ->
                        setState {
                            copy(
                                path = selectedPath,
                                listData = listData,
                                chartData = chartData
                            )
                        }
                    }
            }
        }
    }
}
