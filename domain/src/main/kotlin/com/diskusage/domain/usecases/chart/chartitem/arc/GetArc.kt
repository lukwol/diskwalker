package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot


/**
 *  Usecase for calculating [Arc]
 */
class GetArc(
    private val getStartAngle: GetStartAngle,
    private val getSweepAngle: GetSweepAngle,
    private val getDepth: GetDepth,
    private val getRoot: GetRoot,
) {
    /**
     * Calculate the [Arc] for [DiskEntry] starting from its root or specific [DiskEntry].
     *
     * @param diskEntry [DiskEntry] for which the [Arc] is calculated
     * @param fromDiskEntry Currently selected [DiskEntry] from which calculation should start
     * @return [Arc] with calculated start and sweep angles and depth
     */
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Arc = Arc(
        startAngle = getStartAngle(diskEntry, fromDiskEntry),
        sweepAngle = getSweepAngle(diskEntry, fromDiskEntry),
        depth = getDepth(diskEntry, fromDiskEntry)
    )
}
