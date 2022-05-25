package com.diskusage.presentation.components.chart.blocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.diskusage.domain.common.Constants
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
            .forEach(::draw)
    }
}

private fun isHidden(chartItem: ChartItem): Boolean = with(chartItem) {
    return arc.depth == 0f || arc.sweepAngle == 0f || color.alpha == 0f
}

private fun DrawScope.draw(chartItem: ChartItem) = with(chartItem) {
    val scaledArcWidth = if (arc.depth < 1) Constants.ArcWidth * arc.depth else Constants.ArcWidth
    val radius = Constants.ArcWidth * arc.depth
    val arcSize = 2 * radius - scaledArcWidth

    drawArc(
        color = color,
        startAngle = arc.startAngle,
        sweepAngle = arc.sweepAngle,
        topLeft = Offset(
            x = size.width - arcSize,
            y = size.height - arcSize
        ) / 2f,
        size = Size(
            width = arcSize,
            height = arcSize
        ),
        style = Stroke(width = scaledArcWidth),
        useCenter = false
    )
}
