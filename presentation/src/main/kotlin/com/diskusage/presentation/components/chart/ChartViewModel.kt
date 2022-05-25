package com.diskusage.presentation.components.chart

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.chartitem.GetChartItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChartViewModel(
    diskEntry: DiskEntry,
    private val getChartItems: GetChartItems,
) {
    private val mutableViewState = MutableStateFlow(
        ChartViewState(
            diskEntry = diskEntry,
            startItems = getChartItems(diskEntry)
        )
    )
    val viewState = mutableViewState.asStateFlow()

    fun onSelectChartItem(chartItem: ChartItem) {
        val diskEntry = if (chartItem.diskEntry == viewState.value.diskEntry) {
            chartItem.diskEntry.parent
        } else {
            chartItem.diskEntry
        }

        if (diskEntry != null) {
            val (startItems, endItems) = getChartItems(
                fromDiskEntry = viewState.value.diskEntry,
                toDiskEntry = diskEntry
            )
            mutableViewState.value = mutableViewState.value.copy(
                diskEntry = diskEntry,
                startItems = startItems,
                endItems = endItems
            )
        }
    }
}
