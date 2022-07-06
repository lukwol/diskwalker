package com.diskusage.domain.stubs

import com.diskusage.domain.common.Constants.Chart.BigArcWidth
import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.SmallArcWidth
import com.diskusage.domain.entities.Arc
import com.diskusage.ranges.until

object ArcStubs {
    val hidden = Arc(
        angleRange = 0f until 360f,
        radiusRange = 0f until 0f
    )
    val selected = Arc(
        angleRange = 0f until 360f,
        radiusRange = 0f until BigArcWidth,
    )
    val child1 = Arc(
        angleRange = 0f until 45f,
        radiusRange = BigArcWidth until 2 * BigArcWidth,
    )
    val child2 = Arc(
        angleRange = 45f until 105f,
        radiusRange = BigArcWidth until 2 * BigArcWidth,
    )
    val far = Arc(
        angleRange = 30f until 90f,
        radiusRange = MaxBigArcsDepth * BigArcWidth + 5 * SmallArcWidth until MaxBigArcsDepth * BigArcWidth + 6 * SmallArcWidth
    )
}
