package com.diskusage.presentation.components.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.ChartItemsCollection
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItemsCollection
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetSortedChartItems
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
import com.diskusage.domain.usecases.chart.listItem.GetListItem
import com.diskusage.domain.usecases.chart.listItem.GetSortedListItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getSortedChartItems: GetSortedChartItems,
    private val getSortedListItems: GetSortedListItems,
    private val getListItem: GetListItem,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val mutableViewState = MutableStateFlow(ChartViewState(diskEntry))

    val viewState = mutableViewState.asStateFlow()

    init {
        with(viewState.value) {
            viewModelScope.launch {
                mutableViewState.value = copy(
                    listItemsCollection = ListItemsCollection(
                        selectedItem = getListItem(diskEntry),
                        childItems = getSortedListItems(diskEntry)
                    ),
                    chartItemsCollection = ChartItemsCollection(
                        startItems = getSortedChartItems(diskEntry)
                    )
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
        viewModelScope.launch {
            mutableViewState.value = copy(
                listItemsCollection = ListItemsCollection(
                    selectedItem = getListItem(
                        diskEntry = selectedDiskEntry,
                        fromDiskEntry = diskEntry
                    ),
                    childItems = getSortedListItems(
                        diskEntry = selectedDiskEntry,
                        fromDiskEntry = diskEntry
                    )
                )
            )
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
                val (startChartItems, endChartItems) = getSortedChartItems(
                    fromDiskEntry = diskEntry,
                    toDiskEntry = selectedDiskEntry
                )

                mutableViewState.value = copy(
                    diskEntry = selectedDiskEntry,
                    listItemsCollection = ListItemsCollection(
                        selectedItem = getListItem(selectedDiskEntry),
                        childItems = getSortedListItems(selectedDiskEntry)
                    ),
                    chartItemsCollection = ChartItemsCollection(
                        startItems = startChartItems,
                        endItems = endChartItems
                    )
                )
            }
        }
    }
}
