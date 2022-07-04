package com.diskusage.domain.common

object Constants {

    const val MaxBigArcsDepth = 6

    const val MaxSmallArcsDepth = 8

    const val MaxArcsDepth = MaxBigArcsDepth + MaxSmallArcsDepth

    /**
     * Width of an [Arc][com.diskusage.domain.entities.Arc] that are drawn for each item close to the center of the chart.
     */
    const val BigArcWidth = 100f

    /**
     * Width of an [Arc][com.diskusage.domain.entities.Arc] that are drawn for each item far from the center of the chart.
     */
    const val SmallArcWidth = 20f

    /**
     * Minimum size of an item compared to currently selected root item.
     * Entries that are smaller than [MinChartItemAngle] of the root item are not displayed.
     */
    const val MinChartItemAngle = 0.005f

    const val ChartRadius = MaxBigArcsDepth * BigArcWidth + MaxSmallArcsDepth * SmallArcWidth
}
