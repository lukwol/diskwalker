package com.diskusage.presentation.screens.chart.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc

fun DrawScope.drawArc(arc: Arc, color: Color) {
    if (arc.depth == 0f || arc.sweepAngle == 0f || color.alpha == 0f) return

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
