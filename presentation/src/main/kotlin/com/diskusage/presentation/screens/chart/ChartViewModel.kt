package com.diskusage.presentation.screens.chart

import com.diskusage.domain.entities.ChartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.file.Path

class ChartViewModel {
    private val mutableViewState = MutableStateFlow(ChartViewState())
    val viewState = mutableViewState.asStateFlow()

    fun selectScannedPath(path: Path) {
        // TODO: 15/05/2022 Implement
    }

    fun selectDiskEntry(selectedChartItem: ChartItem) {
        // TODO: 15/05/2022 Implement
    }
}
