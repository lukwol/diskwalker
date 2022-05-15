package com.diskusage.domain.usecases

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

class GetChartItem(
    private val getArc: GetArc,
    private val getColor: GetColor,
) {
    operator fun invoke(diskEntry: DiskEntry) = ChartItem(
        diskEntry = diskEntry,
        arc = getArc(diskEntry),
        color = getColor(diskEntry)
    )
}