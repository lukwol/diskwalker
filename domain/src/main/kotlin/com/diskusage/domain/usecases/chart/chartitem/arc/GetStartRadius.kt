package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants.BigArcWidth
import com.diskusage.domain.common.Constants.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.SmallArcWidth
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRoot

class GetStartRadius(
    private val getRoot: GetRoot,
    private val getDepth: GetDepth,
) {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = getRoot(diskEntry),
    ): Float {
        val depth = getDepth(diskEntry, fromDiskEntry)
        return if (depth <= MaxBigArcsDepth) {
            (depth * BigArcWidth - BigArcWidth).coerceAtLeast(0f)
        } else {
            MaxBigArcsDepth * BigArcWidth + ((depth - MaxBigArcsDepth) * SmallArcWidth - SmallArcWidth).coerceAtLeast(0f)
        }
    }
}
