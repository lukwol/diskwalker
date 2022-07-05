package com.diskusage.presentation.components.chart.blocks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.diskusage.domain.common.Constants.Chart.BigArcWidth
import com.diskusage.domain.common.Constants.Chart.MaxBigArcsDepth
import com.diskusage.domain.common.Constants.Chart.MaxSmallArcsDepth
import com.diskusage.domain.common.Constants.Chart.SmallArcWidth
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
    return arc.width == 0f || arc.sweepAngle == 0f || color.alpha == 0f
}

private fun DrawScope.draw(chartItem: ChartItem) = with(chartItem) {
    val arcSize = 2 * arc.radiusRange.end - arc.width
    val chartRadius = MaxBigArcsDepth * BigArcWidth + MaxSmallArcsDepth * SmallArcWidth
    val angleSpacer = (chartRadius / arc.radiusRange.end) / 10f
    val levelSpacer = 1.3f

    drawArc(
        color = color,
        startAngle = when (arc.sweepAngle) {
            360f -> arc.angleRange.start
            else -> arc.angleRange.start + angleSpacer
        },
        sweepAngle = when (arc.sweepAngle) {
            360f -> arc.sweepAngle
            else -> arc.sweepAngle - angleSpacer
        },
        topLeft = Offset(
            x = size.width - arcSize + levelSpacer,
            y = size.height - arcSize + levelSpacer
        ) / 2f,
        size = Size(
            width = arcSize - levelSpacer,
            height = arcSize - levelSpacer
        ),
        style = Stroke(
            width = (arc.width - levelSpacer).coerceAtLeast(0f)
        ),
        useCenter = false
    )
}
