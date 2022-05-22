package com.diskusage.domain.usecases

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry

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
    ): Pair<List<ChartItem>, List<ChartItem>> {
        return (getDiskEntries(fromDiskEntry) + getDiskEntries(toDiskEntry))
            .distinctBy(DiskEntry::path)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
            .unzip()
    }
}
