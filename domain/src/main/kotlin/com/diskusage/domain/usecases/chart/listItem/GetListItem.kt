package com.diskusage.domain.usecases.chart.listItem

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItem
import com.diskusage.domain.usecases.chart.chartitem.GetColor

/**
 * Creates [ListItem] for given `diskEntry` starting from `fromDiskEntry`.
 */
class GetListItem(
    private val getColor: GetColor
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = ListItem(
        diskEntry = diskEntry,
        color = getColor(diskEntry, fromDiskEntry)
    )
}
