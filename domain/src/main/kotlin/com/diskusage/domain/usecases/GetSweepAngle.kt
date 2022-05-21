package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry
import java.lang.Float.max

class GetSweepAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Float {
        val size = diskEntry.size.toFloat() / fromDiskEntry.size.toFloat()
        return max(size.takeIf(Float::isFinite) ?: 0f, 360f)
    }
}
