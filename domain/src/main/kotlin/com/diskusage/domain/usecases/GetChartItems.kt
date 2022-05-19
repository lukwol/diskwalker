package com.diskusage.domain.usecases

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

class GetChartItems(
    private val getDiskEntries: GetDiskEntries,
    private val getChartItem: GetChartItem,
) {
    operator fun invoke(diskEntry: DiskEntry): List<ChartItem> =
        getDiskEntries(diskEntry)
            .map { getChartItem(it, diskEntry) }

    operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry,
    ): List<Pair<ChartItem, ChartItem>> {
        return (getDiskEntries(fromDiskEntry) + getDiskEntries(toDiskEntry))
            .distinctBy(DiskEntry::path)
            .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
    }
}
