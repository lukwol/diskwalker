package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntries
import com.diskusage.domain.usecases.chart.SortDiskEntries

class GetChartItems(
    private val getDiskEntries: GetDiskEntries,
    private val getChartItem: GetChartItem,
    private val sortDiskEntries: SortDiskEntries,
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
}
