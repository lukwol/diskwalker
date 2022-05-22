package com.diskusage.domain.usecases

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

class GetChartItem(
    private val getArc: GetArc,
    private val getColor: GetColor,
    private val getRoot: GetRoot,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ) = ChartItem(
        diskEntry = diskEntry,
        arc = getArc(diskEntry, fromDiskEntry),
        color = getColor(diskEntry, fromDiskEntry)
    )
}
