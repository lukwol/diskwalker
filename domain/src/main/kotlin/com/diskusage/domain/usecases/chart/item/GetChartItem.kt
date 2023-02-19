package com.diskusage.domain.usecases.chart.item

import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.usecases.chart.item.arc.GetArc
import com.diskusage.domain.usecases.diskentry.GetRoot
import java.nio.file.Path

/**
 * Creates [ChartItem] for given `diskEntry` starting from `fromDiskEntry`.
 */
class GetChartItem(
    private val getRoot: GetRoot,
    private val getArc: GetArc,
    private val getColor: GetColor
) {
    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ): ChartItem {
        val arc = getArc(path, fromPath)
        return ChartItem(
            path = path,
            arc = arc,
            color = getColor(path, fromPath, arc)
        )
    }
}
