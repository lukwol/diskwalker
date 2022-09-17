package com.diskusage.presentation.components.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetChartData
import com.diskusage.domain.usecases.chart.chartitem.GetListData
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val mutableViewState = MutableStateFlow(ChartViewState(diskEntry))

    val viewState = mutableViewState.asStateFlow()

    init {
        with(viewState.value) {
            viewModelScope.launch {
                val listData = async { getListData(diskEntry) }
                val chartData = async { getChartData(diskEntry) }

                mutableViewState.value = copy(
                    listData = listData.await(),
                    chartData = chartData.await()
                )
            }
        }
    }

    fun onChartPositionHovered(position: Offset) = with(viewState.value) {
        if (chartData != null) {
            val (startItems, endItems) = chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                .let(::onHoverDiskEntry)
        }
    }

    fun onChartPositionClicked(position: Offset) = with(viewState.value) {
        if (chartData != null) {
            val (startItems, endItems) = chartData
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                ?.takeIf { it.diskEntry.type == DiskEntry.Type.Directory }
                ?.let(::onSelectChartItem)
        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) = with(viewState.value) {
        val selectedDiskEntry = chartItem?.diskEntry ?: diskEntry
        if (selectedDiskEntry != listData?.parentItem?.diskEntry) {
            viewModelScope.launch {
                mutableViewState.value = copy(
                    listData = getListData(
                        diskEntry = selectedDiskEntry,
                        fromDiskEntry = diskEntry
                    )
                )
            }
        }
    }

    fun onSelectChartItem(chartItem: ChartItem) = with(viewState.value) {
        val selectedDiskEntry = if (chartItem.diskEntry == diskEntry) {
            chartItem.diskEntry.parent
        } else {
            chartItem.diskEntry
        }

        if (selectedDiskEntry != null) {
            viewModelScope.launch {
                val listData = async {
                    getListData(selectedDiskEntry)
                }
                val chartData = async {
                    getChartData(
                        fromDiskEntry = diskEntry,
                        toDiskEntry = selectedDiskEntry
                    )
                }
                mutableViewState.value = copy(
                    diskEntry = selectedDiskEntry,
                    listData = listData.await(),
                    chartData = chartData.await()
                )
            }
        }
    }
}
