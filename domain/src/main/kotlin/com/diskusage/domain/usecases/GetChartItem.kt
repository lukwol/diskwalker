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
    ): ChartItem {
        val arc = getArc(diskEntry, fromDiskEntry)
        return ChartItem(
            diskEntry = diskEntry,
            arc = arc,
            color = getColor(arc, diskEntry, fromDiskEntry)
        )
    }
}
