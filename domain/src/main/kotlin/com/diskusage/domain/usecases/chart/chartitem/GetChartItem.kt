package com.diskusage.domain.usecases.chart.chartitem

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot

/**
 * Creates [ChartItem] for given `diskEntry` starting from `fromDiskEntry`.
 *
 * If [ChartItem] won't [be visible on chart][includeDiskEntry], set's it's [alpha][Color.alpha] value to **0f**.
 */
class GetChartItem(
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor,
    private val includeDiskEntry: IncludeDiskEntry
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry)
    ): ChartItem {
        val arc = getArc(diskEntry, fromDiskEntry)
        val color = getColor(diskEntry, fromDiskEntry, arc)
        val isIncluded = includeDiskEntry(diskEntry, fromDiskEntry)
        return ChartItem(
            diskEntry = diskEntry,
            arc = arc,
            color = color.copy(alpha = if (isIncluded) 1f else 0f)
        )
    }
}
