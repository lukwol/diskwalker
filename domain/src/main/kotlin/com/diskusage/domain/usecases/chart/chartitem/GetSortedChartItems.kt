package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetSortedChartItems(
    private val getDiskEntriesList: GetDiskEntriesList,
    private val sortDiskEntries: SortDiskEntries,
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor,
) {
    /**
     * Get list of sorted [chart items][ChartItem] required for drawing actual chart.
     *
     * @param diskEntry [DiskEntry] for which the [chart items][ChartItem] are created
     * @return [List] of sorted [chart items][ChartItem]
     * @see sortDiskEntries
     */
    operator fun invoke(diskEntry: DiskEntry): List<ChartItem> =
        getDiskEntriesList(diskEntry)
            .let(sortDiskEntries::invoke)
            .map { getChartItem(it, diskEntry) }

    /**
     * Get a pair of sorted [chart items][ChartItem] lists required for drawing and animating chart.
     *
     * Chart will start drawing from the first list based on [fromDiskEntry]
     * and animate to the final list based on [toDiskEntry].
     *
     * @param fromDiskEntry [DiskEntry] determines what will be the starting list of [chart items][ChartItem]
     * @param toDiskEntry [DiskEntry] determines what will be the final list of [chart items][ChartItem]
     * @return a pair of sorted [chart items][ChartItem] lists
     */
    operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry,
    ) = (getDiskEntriesList(fromDiskEntry) + getDiskEntriesList(toDiskEntry))
        .distinctBy(DiskEntry::path)
        .let(sortDiskEntries::invoke)
        .map { getChartItem(it, fromDiskEntry) to getChartItem(it, toDiskEntry) }
        .unzip()

    /**
     * Create [ChartItem] based on given [diskEntry] starting calculation from [fromDiskEntry].
     *
     * @param diskEntry [DiskEntry] for which the [ChartItem] is created
     * @param fromDiskEntry [DiskEntry] from which calculation of arc and color will start, uses root if not given
     * @return [ChartItem] with calculated [arc][getArc] and [color][getColor]
     */
    private fun getChartItem(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): ChartItem {
        val arc = getArc(diskEntry, fromDiskEntry)
        return ChartItem(
            diskEntry = diskEntry,
            arc = arc,
            color = getColor(diskEntry, fromDiskEntry, arc)
        )
    }
}
