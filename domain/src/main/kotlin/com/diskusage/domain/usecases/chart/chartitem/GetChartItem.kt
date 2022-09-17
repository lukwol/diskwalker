package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot

/**
 * Creates [ChartItem] for given `diskEntry` starting from `fromDiskEntry`.
 */
class GetChartItem(
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ): ChartItem {
        val arc = getArc(diskEntry, fromDiskEntry)
        return ChartItem(
            diskEntry = diskEntry,
            arc = arc,
            color = getColor(diskEntry, fromDiskEntry, arc)
        )
    }
}
