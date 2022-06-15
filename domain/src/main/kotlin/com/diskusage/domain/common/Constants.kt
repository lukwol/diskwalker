package com.diskusage.domain.common

object Constants {
    /**
     * Maximum number of layers displayed in the Chart in given moment.
     * Entries that are nested more than 10 levels from root item are not displayed.
     */
    const val MaxChartDepth = 10

    /**
     * Minimum size of an item compared to currently selected root item.
     * Entries that are smaller than 0.5% of the root item are not displayed.
     */
    const val MinChartItemSize = 0.005f

    /**
     * Default width of an [Arc][com.diskusage.domain.entities.Arc] that are drawn on a Chart for each item.
     */
    const val ArcWidth = 100f
}
