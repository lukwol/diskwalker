package com.diskusage.presentation.components.chart

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetSortedChartItems
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
class ChartViewModel(
    @InjectedParam diskEntry: DiskEntry,
    private val getSortedChartItems: GetSortedChartItems,
    private val includeDiskEntry: IncludeDiskEntry,
    private val isArcSelected: IsArcSelected,
) {
    private val mutableViewState = MutableStateFlow(
        ChartViewState(
            diskEntry = diskEntry,
            startItems = getSortedChartItems(diskEntry)
        )
    )
    val viewState = mutableViewState.asStateFlow()

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
            val (startItems, endItems) = getSortedChartItems(
                fromDiskEntry = diskEntry,
                toDiskEntry = selectedDiskEntry
            )
            mutableViewState.value = copy(
                diskEntry = selectedDiskEntry,
                startItems = startItems,
                endItems = endItems
            )
        }
    }
}
