package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetArc(
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Arc = Arc(
        startAngle = getStartAngle(diskEntry, fromDiskEntry),
        sweepAngle = getSweepAngle(diskEntry, fromDiskEntry),
        depth = getDepth(diskEntry, fromDiskEntry)
    )
}
