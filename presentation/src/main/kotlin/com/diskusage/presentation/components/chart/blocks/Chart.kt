package com.diskusage.presentation.components.chart.blocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.diskusage.domain.entities.ChartItem

@Composable
fun Chart(
    chartItems: List<ChartItem>,
    modifier: Modifier = Modifier,
) {
    Canvas(
        Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        chartItems
            .filterNot(::isHidden)
            .reversed()
            .forEach(::draw)
    }
}

private fun isHidden(chartItem: ChartItem): Boolean = with(chartItem) {
    return arc.width == 0f || arc.sweepAngle == 0f || color.alpha == 0f
}

private fun DrawScope.draw(chartItem: ChartItem) = with(chartItem) {
    val arcSize = 2 * arc.radiusRange.endInclusive - arc.width

    drawArc(
        color = color,
        startAngle = arc.angleRange.start,
        sweepAngle = arc.sweepAngle,
        topLeft = Offset(
            x = size.width - arcSize,
            y = size.height - arcSize
        ) / 2f,
        size = Size(
            width = arcSize,
            height = arcSize
        ),
        style = Stroke(width = arc.width),
        useCenter = false
    )
}
