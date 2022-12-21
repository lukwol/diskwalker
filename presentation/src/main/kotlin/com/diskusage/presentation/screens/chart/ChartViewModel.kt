package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.arc.IsArcSelected
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.list.GetListData
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) : ViewModel(), AsyncState<ChartViewState> by AsyncState.Delegate(ChartViewState(diskEntry)) {

    init {
        viewModelScope.launch {
            val listData = async { getListData(diskEntry) }
            val chartData = async { getChartData(diskEntry) }
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
            is OnSelectDiskEntry -> onSelectDiskEntry(diskEntry)
        }
    }

    fun onChartPositionHovered(position: Offset) {
        val chartData = withState(ChartViewState::chartData)
        val diskEntry = withState(ChartViewState::diskEntry)
        if (chartData != null) {
            val (startItems, endItems) = chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                .let(::onHoverDiskEntry)
        }
    }

    fun onChartPositionClicked(position: Offset) {
        val chartData = withState(ChartViewState::chartData)
        val diskEntry = withState(ChartViewState::diskEntry)
        if (chartData != null) {
            val (startItems, endItems) = chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                ?.let(ChartItem::diskEntry)
                ?.let(::onSelectDiskEntry)
        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) {
        val diskEntry = withState(ChartViewState::diskEntry)
        val selectedDiskEntry = chartItem?.diskEntry ?: diskEntry
        if (selectedDiskEntry != withState(ChartViewState::listData)?.parentItem?.diskEntry) {
            viewModelScope.launch {
                withTimeoutOrNull(Constants.HeavyOperationsTimeout) {
                    val listData = getListData(
                        diskEntry = selectedDiskEntry,
                        fromDiskEntry = diskEntry
                    )
                    setState {
                        copy(listData = listData)
                    }
                }
            }
        }
    }

    fun onSelectDiskEntry(diskEntry: DiskEntry) {
        val previousDiskEntry = withState(ChartViewState::diskEntry)
        val selectedDiskEntry = when {
            diskEntry.type != DiskEntry.Type.Directory -> null
            diskEntry.sizeOnDisk == 0L -> null
            diskEntry == previousDiskEntry -> diskEntry.parent
            else -> diskEntry
        }

        if (selectedDiskEntry != null) {
            viewModelScope.launch {
                val listData = async {
                    getListData(selectedDiskEntry)
                }
                val chartData = async {
                    getChartData(
                        fromDiskEntry = previousDiskEntry,
                        toDiskEntry = selectedDiskEntry
                    )
                }
                (listData.await() to chartData.await())
                    .let { (listData, chartData) ->
                        setState {
                            copy(
                                diskEntry = selectedDiskEntry,
                                listData = listData,
                                chartData = chartData
                            )
                        }
                    }
            }
        }
    }
}
