package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetStartAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Float = 0f
}
