package com.diskusage.domain.usecases

import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.DiskEntry

class GetArc {
    operator fun invoke(diskEntry: DiskEntry): Arc {
        // TODO: 15/05/2022 Implement
        return Arc(startAngle = 0.0f, sweepAngle = 0.0f, depth = 0.0f)
    }
}
