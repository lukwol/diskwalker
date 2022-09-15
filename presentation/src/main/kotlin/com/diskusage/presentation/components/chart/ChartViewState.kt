package com.diskusage.presentation.components.chart

import com.diskusage.domain.model.ChartItemsCollection
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItemsCollection

data class ChartViewState(
    val diskEntry: DiskEntry,
    val listItemsCollection: ListItemsCollection? = null,
    val chartItemsCollection: ChartItemsCollection? = null
)
