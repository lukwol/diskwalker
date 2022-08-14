package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Get a sorted [chart items][ChartItem] list required for drawing the chart.
 *
 * Can return a pair of lists for chart to be able to animate from one to another.
 *
 * @see sortDiskEntries
 */
class GetSortedChartItems(
    private val getDiskEntriesList: GetDiskEntriesList,
    private val sortDiskEntries: SortDiskEntries,
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor
) {
    suspend operator fun invoke(diskEntry: DiskEntry) = withContext(Dispatchers.Default) {
        getDiskEntriesList(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }
    }

    suspend operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry
    ) = withContext(Dispatchers.Default) {
        (getDiskEntriesList(fromDiskEntry) + getDiskEntriesList(toDiskEntry))
            .distinctBy(DiskEntry::path)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
            .unzip()
    }

    private fun getChartItem(
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
