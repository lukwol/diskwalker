package com.diskusage.domain.stubs.stubs

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc

object ArcStubs {
    val hidden = Arc(
        angleRange = 0f..360f,
        radiusRange = 0f..0f
    )
    val selected = Arc(
        angleRange = 0f..360f,
        radiusRange = 0f..Constants.ArcWidth,
    )
    val child1 = Arc(
        angleRange = 0f..45f,
        radiusRange = Constants.ArcWidth..2 * Constants.ArcWidth,
    )
    val child2 = Arc(
        angleRange = 45f..105f,
        radiusRange = Constants.ArcWidth..2 * Constants.ArcWidth,
    )
    val far = Arc(
        angleRange = 30f..90f,
        radiusRange = 7 * Constants.ArcWidth..8 * Constants.ArcWidth,
    )
}
