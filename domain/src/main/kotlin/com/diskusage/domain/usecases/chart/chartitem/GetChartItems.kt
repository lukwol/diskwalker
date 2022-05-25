package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntries
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetChartItems(
    private val getDiskEntries: GetDiskEntries,
    private val sortDiskEntries: SortDiskEntries,
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor,
) {
    operator fun invoke(diskEntry: DiskEntry): List<ChartItem> =
        getDiskEntries(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }

    operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry,
    ) = (getDiskEntries(fromDiskEntry) + getDiskEntries(toDiskEntry))
        .distinctBy(DiskEntry::path)
        .let(sortDiskEntries::invoke)
        .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
        .unzip()

    private fun getChartItem(
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