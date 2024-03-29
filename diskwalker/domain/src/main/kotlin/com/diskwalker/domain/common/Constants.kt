package com.diskwalker.domain.common

object Constants {

    /**
     * Chart related constants
     */
    object Chart {

        /**
         * Maximum number of big items layers displayed in the Chart.
         * Items that are nested deeper starting from root item are smaller drawn as smaller items.
         */
        const val MaxBigArcsDepth = 6

        /**
         * Maximum number of small items layers displayed in the Chart.
         * Items that are nested deeper starting from [MaxSmallArcsDepth] are not displayed.
         */
        const val MaxSmallArcsDepth = 8

        /**
         * Maximum number of all layers displayed in the Chart.
         */
        const val MaxArcsDepth = MaxBigArcsDepth + MaxSmallArcsDepth

        /**
         * Width of an [Arc][com.diskwalker.domain.model.Arc] that are drawn for each item close to the center of the chart.
         */
        const val BigArcWidth = 25f

        /**
         * Width of an [Arc][com.diskwalker.domain.model.Arc] that are drawn for each item far from the center of the chart.
         */
        const val SmallArcWidth = 5f

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
