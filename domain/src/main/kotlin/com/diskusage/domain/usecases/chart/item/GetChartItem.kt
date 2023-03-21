package com.diskusage.domain.usecases.chart.item

import com.diskusage.domain.model.chart.ChartItem
import com.diskusage.domain.usecases.chart.item.arc.GetArc
import java.nio.file.Path

/**
 * Creates [ChartItem] for given `path` starting from `fromPath`.
 */
class GetChartItem(
    private val getArc: GetArc,
    private val getColor: GetColor,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
    ): ChartItem {
        val arc = getArc(path, fromPath, disk)
        return ChartItem(
            path = path,
            arc = arc,
            color = getColor(path, fromPath, disk, arc),
        )
    }
}
