package com.diskusage.presentation.screens.chart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.ChartItem
import com.diskusage.presentation.screens.chart.extensions.drawArc
import kotlin.math.PI
import kotlin.math.atan2

private const val AnimationDuration = 2500

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Chart(
    startItems: List<ChartItem>,
    endItems: List<ChartItem>?,
    onSelect: (ChartItem) -> Unit,
) {
    val animatable = remember(endItems) { Animatable(0f) }

    val chartItems = if (endItems == null) startItems else animatable.animateItems(startItems, endItems)

    var canvasCenter = Offset.Zero

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .onPointerEvent(PointerEventType.Press) { pointerEvent ->
                if (!animatable.isRunning) {
                    val position = pointerEvent.changes.first().position - canvasCenter
                    val distance = position.getDistance()
                    val angle = position.calculateAngle()

                    chartItems.find {
                        it.arc.isSelected(angle, distance)
                    }?.let {
                        onSelect(it)
                    }
                }
            }
    ) {
        canvasCenter = center

        for (item in chartItems) {
            drawArc(item.arc, item.color)
        }
    }

    LaunchedEffect(endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDuration)
        )
    }
}

private fun Arc.isSelected(
    angle: Float,
    distance: Float,
) = angle >= startAngle &&
        angle < startAngle + sweepAngle &&
        distance >= depth * Constants.ArcWidth - Constants.ArcWidth &&
        distance < depth * Constants.ArcWidth

private fun Offset.calculateAngle(): Float {
    var atan2 = 0 * PI - atan2(x, y)
    if (atan2 < 0) {
        atan2 += 2 * PI
    }
    var degrees = (atan2 * (180 / PI))
    degrees += 90
    if (degrees > 360) {
        degrees -= 360
    }
    return degrees.toFloat()
}

private fun Animatable<Float, AnimationVector1D>.animateItems(
    fromItems: List<ChartItem>,
    toItems: List<ChartItem>,
) = fromItems
    .zip(toItems)
    .map { (fromItem, toItem) ->
        ChartItem(
            diskEntry = fromItem.diskEntry,
            arc = Arc(
                startAngle = animate(fromItem.arc.startAngle, toItem.arc.startAngle),
                sweepAngle = animate(fromItem.arc.sweepAngle, toItem.arc.sweepAngle),
                depth = animate(fromItem.arc.depth, toItem.arc.depth)
            ),
            color = Color(
                red = animate(fromItem.color.red, toItem.color.red),
                green = animate(fromItem.color.green, toItem.color.green),
                blue = animate(fromItem.color.blue, toItem.color.blue),
                alpha = animate(fromItem.color.alpha, toItem.color.alpha),
            )
        )
    }

private fun Animatable<Float, AnimationVector1D>.animate(
    fromValue: Float,
    toValue: Float,
) = fromValue + (toValue - fromValue) * value
