package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry
import java.lang.Float.max

class GetSweepAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = max(diskEntry.size / fromDiskEntry.size.toFloat(), 360f)
    // TODO: Check for NaN
}
