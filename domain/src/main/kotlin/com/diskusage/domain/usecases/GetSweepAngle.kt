package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetSweepAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ) = diskEntry.size / fromDiskEntry.size.toFloat()
}
