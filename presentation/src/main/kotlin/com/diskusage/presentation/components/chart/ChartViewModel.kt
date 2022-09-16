package com.diskusage.presentation.components.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetChartItemsCollection
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
import com.diskusage.domain.usecases.chart.listItem.GetListItemsCollection
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getChartItemsCollection: GetChartItemsCollection,
    private val getListItemsCollection: GetListItemsCollection,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val mutableViewState = MutableStateFlow(ChartViewState(diskEntry))

    val viewState = mutableViewState.asStateFlow()

    init {
        with(viewState.value) {
            viewModelScope.launch {
                val listItemsCollection = async { getListItemsCollection(diskEntry) }
                val chartItemsCollection = async { getChartItemsCollection(diskEntry) }

                mutableViewState.value = copy(
                    listItemsCollection = listItemsCollection.await(),
                    chartItemsCollection = chartItemsCollection.await()
                )
            }
        }
    }

    fun onChartPositionHovered(position: Offset) = with(viewState.value) {
        if (chartItemsCollection != null) {
            val (startItems, endItems) = chartItemsCollection
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                .let(::onHoverDiskEntry)
        }
    }

    fun onChartPositionClicked(position: Offset) = with(viewState.value) {
        if (chartItemsCollection != null) {
            val (startItems, endItems) = chartItemsCollection
            (endItems ?: startItems)
                .filter { includeDiskEntry(it.diskEntry, diskEntry) }
                .find { isArcSelected(it.arc, position) }
                ?.let(::onSelectChartItem)
        }
    }

    private fun onHoverDiskEntry(chartItem: ChartItem?) = with(viewState.value) {
        val selectedDiskEntry = chartItem?.diskEntry ?: diskEntry
        if (selectedDiskEntry != listItemsCollection?.parentItem?.diskEntry) {
            viewModelScope.launch {
                mutableViewState.value = copy(
                    listItemsCollection = getListItemsCollection(
                        diskEntry = selectedDiskEntry,
                        fromDiskEntry = diskEntry
                    )
                )
            }
        }
    }

    private fun onSelectChartItem(chartItem: ChartItem) = with(viewState.value) {
        val selectedDiskEntry = if (chartItem.diskEntry == diskEntry) {
            chartItem.diskEntry.parent
        } else {
            chartItem.diskEntry
        }

        if (selectedDiskEntry != null) {
            viewModelScope.launch {
                val listItemsCollection = async {
                    getListItemsCollection(selectedDiskEntry)
                }
                val chartItemsCollection = async {
                    getChartItemsCollection(
                        fromDiskEntry = diskEntry,
                        toDiskEntry = selectedDiskEntry
                    )
                }
                mutableViewState.value = copy(
                    diskEntry = selectedDiskEntry,
                    listItemsCollection = listItemsCollection.await(),
                    chartItemsCollection = chartItemsCollection.await()
                )
            }
        }
    }
}
