package com.diskusage.domain.common

object Constants {

    /**
     * Chart related constants
     */
    object Chart {
        /**
         * Maximum number of big items layers displayed in the Chart in given moment.
         * Items that are nested deeper starting from root item are smaller drawn as smaller items.
         */
        const val MaxBigArcsDepth = 6

        /**
         * Maximum number of small items layers displayed in the Chart in given moment.
         * Items that are nested deeper starting from [MaxSmallArcsDepth] are not displayed.
         */
        const val MaxSmallArcsDepth = 8

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

        /**
         * Duration of chart items animation in milliseconds
         */
        const val AnimationDurationMillis = 1000
    }
}
