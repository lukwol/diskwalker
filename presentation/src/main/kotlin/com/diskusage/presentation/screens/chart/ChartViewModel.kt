package com.diskusage.presentation.screens.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.arc.IsArcSelected
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.list.GetListData
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getChartData: GetChartData,
    private val getListData: GetListData,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) : ViewModel() {

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
                ?.let(ChartItem::diskEntry)
                ?.let(::onSelectDiskEntry)
        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) = with(viewState.value) {
        val selectedDiskEntry = chartItem?.diskEntry ?: diskEntry
        if (selectedDiskEntry != listData?.parentItem?.diskEntry) {
            viewModelScope.launch {
                withTimeoutOrNull(Constants.HeavyOperationsTimeout) {
                    mutableViewState.value = copy(
                        listData = getListData(
                            diskEntry = selectedDiskEntry,
                            fromDiskEntry = diskEntry
                        )
                    )
                }
            }
        }
    }

    fun onSelectDiskEntry(diskEntry: DiskEntry) = with(viewState.value) {
        val previousDiskEntry = this.diskEntry
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
                mutableViewState.value = copy(
                    diskEntry = selectedDiskEntry,
                    listData = listData.await(),
                    chartData = chartData.await()
                )
            }
        }
    }
}
