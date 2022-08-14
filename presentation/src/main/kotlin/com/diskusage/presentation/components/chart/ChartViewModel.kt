package com.diskusage.presentation.components.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetSortedChartItems
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
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
                    listItems = getSortedListItems(diskEntry),
                    startItems = getSortedChartItems(diskEntry)
                )
            }
        }
    }

    fun onChartPositionClicked(position: Offset) = with(viewState.value) {
        (endItems ?: startItems)
            .filter { includeDiskEntry(it.diskEntry, diskEntry) }
            .find { isArcSelected(it.arc, position) }
            ?.let(::onSelectChartItem)
    }

    private fun onSelectChartItem(chartItem: ChartItem) = with(viewState.value) {
        val selectedDiskEntry = if (chartItem.diskEntry == diskEntry) {
            chartItem.diskEntry.parent
        } else {
            chartItem.diskEntry
        }

        if (selectedDiskEntry != null) {
            viewModelScope.launch {
                val (startItems, endItems) = getSortedChartItems(
                    fromDiskEntry = diskEntry,
                    toDiskEntry = selectedDiskEntry
                )

                mutableViewState.value = copy(
                    diskEntry = selectedDiskEntry,
                    listItems = getSortedListItems(selectedDiskEntry),
                    startItems = startItems,
                    endItems = endItems
                )
            }
        }
    }
}
