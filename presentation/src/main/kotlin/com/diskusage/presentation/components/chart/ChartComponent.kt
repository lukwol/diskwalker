@file:OptIn(ExperimentalComposeUiApi::class)

package com.diskusage.presentation.components.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.components.chart.blocks.Chart
import kotlin.math.PI
import kotlin.math.atan2

private const val AnimationDuration = 1000

@Composable
fun ChartComponent(diskEntry: DiskEntry) {
    val viewModel = remember { ViewModelProvider.getChartViewModel(diskEntry) }
    val viewState by viewModel.viewState.collectAsState()

    val startItems = viewState.startItems
    val endItems = viewState.endItems

    val animatable = remember(endItems) { Animatable(0f) }

    val chartItems = when {
        endItems == null -> startItems
        animatable.value < 1f -> intermediateItems(animatable.value, startItems, endItems)
        else -> endItems
    }

    Chart(
        chartItems = chartItems,
        modifier = Modifier.onPointerEvent(PointerEventType.Press) { pointerEvent ->
            if (!animatable.isRunning) {
                val position = pointerEvent.changes.first().position - size.center.toOffset()
                val distance = position.getDistance()
                val angle = position.calculateAngle()

                chartItems
                    .filterNot { it.color.alpha == 0f }
                    .find {
                        it.arc.isSelected(angle, distance)
                    }?.let {
                        viewModel.onSelectChartItem(it)
                    }
            }
        }
    )

    LaunchedEffect(endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDuration)
        )
    }
}

// Extract to IsArcSelected
private fun Arc.isSelected(
    angle: Float,
    distance: Float,
) = angle >= startAngle &&
    angle < startAngle + sweepAngle &&
    distance >= depth * Constants.ArcWidth - Constants.ArcWidth &&
    distance < depth * Constants.ArcWidth

// Extract to CalculateAngle
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

// Extract to GetIntermediateChartItems
private fun intermediateItems(
    proportion: Float,
    fromItems: List<ChartItem>,
    toItems: List<ChartItem>,
) = fromItems.zip(toItems)
    .map { (fromItem, toItem) ->
        intermediateItem(proportion, fromItem, toItem)
    }

private fun intermediateItem(
    proportion: Float,
    fromItem: ChartItem,
    toItem: ChartItem,
) = ChartItem(
    diskEntry = fromItem.diskEntry,
    arc = intermediateArc(proportion, fromItem.arc, toItem.arc),
    color = intermediateColor(proportion, fromItem.color, toItem.color)
)

private fun intermediateArc(
    proportion: Float,
    fromArc: Arc,
    toArc: Arc,
) = Arc(
    startAngle = intermediateValue(proportion, fromArc.startAngle, toArc.startAngle),
    sweepAngle = intermediateValue(proportion, fromArc.sweepAngle, toArc.sweepAngle),
    depth = intermediateValue(proportion, fromArc.depth, toArc.depth)
)

private fun intermediateColor(
    proportion: Float,
    fromColor: Color,
    toColor: Color,
) = Color(
    red = intermediateValue(proportion, fromColor.red, toColor.red),
    green = intermediateValue(proportion, fromColor.green, toColor.green),
    blue = intermediateValue(proportion, fromColor.blue, toColor.blue),
    alpha = intermediateValue(proportion, fromColor.alpha, toColor.alpha),
)

private fun intermediateValue(
    proportion: Float,
    fromValue: Float,
    toValue: Float,
) = fromValue + (toValue - fromValue) * proportion
