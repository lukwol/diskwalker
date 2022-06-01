package com.diskusage.domain.stubs.stubs

import com.diskusage.domain.entities.Arc

object ArcStubs {
    val zero = Arc(
        startAngle = 0f,
        sweepAngle = 0f,
        depth = 0f
    )
    val hidden = Arc(
        startAngle = 0f,
        sweepAngle = 360f,
        depth = 0f
    )
    val selected = Arc(
        startAngle = 0f,
        sweepAngle = 360f,
        depth = 1f
    )
    val child1 = Arc(
        startAngle = 0f,
        sweepAngle = 45f,
        depth = 2f
    )
    val child2 = Arc(
        startAngle = 45f,
        sweepAngle = 60f,
        depth = 2f
    )
    val far = Arc(
        startAngle = 30f,
        sweepAngle = 60f,
        depth = 8f
    )
}
