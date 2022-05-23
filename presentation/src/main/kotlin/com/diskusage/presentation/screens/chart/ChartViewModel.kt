package com.diskusage.presentation.screens.chart

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.usecases.GetChartItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.file.Path

class ChartViewModel(
    private val diskEntryRepository: DiskEntryRepository,
    private val getChartItems: GetChartItems,
) {
    private val mutableViewState = MutableStateFlow(ChartViewState())
    val viewState = mutableViewState.asStateFlow()

    fun selectScannedPath(path: Path) {
        val diskEntry = diskEntryRepository.diskEntry(path)
        val startItems = getChartItems(diskEntry = diskEntry)
        mutableViewState.value = mutableViewState.value.copy(
            selectedDiskEntry = diskEntry,
            startItems = startItems
        )
    }

    fun selectDiskEntry(selectedChartItem: ChartItem) {
        val diskEntry = if (selectedChartItem.diskEntry == viewState.value.selectedDiskEntry) {
            selectedChartItem.diskEntry.parent
        } else {
            selectedChartItem.diskEntry
        }

        val fromDiskEntry = viewState.value.selectedDiskEntry

        if (fromDiskEntry != null && diskEntry != null) {
            val (startItems, endItems) = getChartItems(fromDiskEntry = fromDiskEntry, toDiskEntry = diskEntry)
            mutableViewState.value = mutableViewState.value.copy(
                selectedDiskEntry = diskEntry,
                startItems = startItems,
                endItems = endItems
            )
        }
    }
}
