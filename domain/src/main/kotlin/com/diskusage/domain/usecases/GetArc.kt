package com.diskusage.domain.usecases

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry

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
