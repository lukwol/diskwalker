package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.arc.IsArcSelected
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.list.GetListData
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import java.nio.file.Path

class ChartViewModel(
    path: Path,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) : ViewModel(), AsyncState<ChartViewState> by AsyncState.Delegate(ChartViewState(path)) {

    init {
//        viewModelScope.launch {
//            val listData = async { getListData(diskEntry) }
//            val chartData = async { getChartData(diskEntry) }
//            (listData.await() to chartData.await())
//                .let { (listData, chartData) ->
//                    setState {
//                        copy(
//                            listData = listData,
//                            chartData = chartData
//                        )
//                    }
//                }
//        }
    }

    fun onCommand(command: ChartCommand) = with(command) {
        when (this) {
            is OnChartPositionClicked -> onChartPositionClicked(position)
            is OnChartPositionHovered -> onChartPositionHovered(position)
            is OnHoverDiskEntry -> onHoverDiskEntry(chartItem)
            is OnSelectDiskEntry -> onSelectDiskEntry(diskEntry)
        }
    }

    private fun onChartPositionHovered(position: Offset) = withState { state ->
//        if (state.chartData != null) {
//            val (startItems, endItems) = state.chartData
//            (endItems ?: startItems)
//                .filter { includeDiskEntry(it.diskEntry, state.diskEntry) }
//                .find { isArcSelected(it.arc, position) }
//                .let(::onHoverDiskEntry)
//        }
    }

    private fun onChartPositionClicked(position: Offset) = withState { state ->
//        if (state.chartData != null) {
//            val (startItems, endItems) = state.chartData
//            (endItems ?: startItems)
//                .filter { includeDiskEntry(it.diskEntry, state.diskEntry) }
//                .find { isArcSelected(it.arc, position) }
//                ?.let(ChartItem::diskEntry)
//                ?.let(::onSelectDiskEntry)
//        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) = withState { state ->
//        val selectedDiskEntry = chartItem?.diskEntry ?: state.diskEntry
//        if (selectedDiskEntry != state.listData?.parentItem?.diskEntry) {
//            viewModelScope.launch {
//                withTimeoutOrNull(Constants.HeavyOperationsTimeout) {
//                    val listData = getListData(
//                        diskEntry = selectedDiskEntry,
//                        fromDiskEntry = state.diskEntry
//                    )
//                    setState {
//                        copy(listData = listData)
//                    }
//                }
//            }
//        }
    }

    private fun onSelectDiskEntry(diskEntry: DiskEntry) = withState { state ->
//        val previousDiskEntry = state.diskEntry
//        val selectedDiskEntry = when {
//            diskEntry.type != DiskEntry.Type.Directory -> null
//            diskEntry.sizeOnDisk == 0L -> null
//            diskEntry == previousDiskEntry -> diskEntry.parent
//            else -> diskEntry
//        }
//
//        if (selectedDiskEntry != null) {
//            viewModelScope.launch {
//                val listData = async {
//                    getListData(selectedDiskEntry)
//                }
//                val chartData = async {
//                    getChartData(
//                        fromDiskEntry = previousDiskEntry,
//                        toDiskEntry = selectedDiskEntry
//                    )
//                }
//                (listData.await() to chartData.await())
//                    .let { (listData, chartData) ->
//                        setState {
//                            copy(
//                                diskEntry = selectedDiskEntry,
//                                listData = listData,
//                                chartData = chartData
//                            )
//                        }
//                    }
//            }
//        }
    }
}
