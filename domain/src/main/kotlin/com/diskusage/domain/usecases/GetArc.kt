package com.diskusage.domain.usecases

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry

class GetArc {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry,
    ): Arc {
        return Arc(startAngle = 0.0f, sweepAngle = 0.0f, depth = 0.0f)
    }
}
