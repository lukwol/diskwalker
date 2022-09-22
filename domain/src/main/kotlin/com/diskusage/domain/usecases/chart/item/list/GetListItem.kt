package com.diskusage.domain.usecases.chart.item.list

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItem
import com.diskusage.domain.usecases.chart.item.chart.GetColor

/**
 * Creates [ListItem] for given `diskEntry` starting from `fromDiskEntry`.
 */
class GetListItem(
    private val getColor: GetColor
) {

    //  TODO: Optimize color calculation based on given chartItem
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry
    ) = ListItem(
        diskEntry = diskEntry,
        color = getColor(diskEntry, fromDiskEntry)
    )
}
