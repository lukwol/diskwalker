@file:OptIn(ExperimentalComposeUiApi::class)

package com.diskusage.presentation.components.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.diskusage.domain.common.Constants.Chart.AnimationDurationMillis
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.libraries.ranges.HalfOpenFloatRange
import com.diskusage.libraries.ranges.until
import com.diskusage.presentation.components.chart.blocks.Chart
import com.diskusage.presentation.components.chart.blocks.ItemsList
import com.diskusage.presentation.di.ViewModelProvider

private const val ChartWeight = 4f
private const val ListWeight = 1f

@Composable
fun ChartComponent(diskEntry: DiskEntry) {
    val viewModel = remember { ViewModelProvider.getChartViewModel(diskEntry) }
    val viewState by viewModel.viewState.collectAsState()

    val listItems = viewState.listItems
    val startItems = viewState.startItems
    val endItems = viewState.endItems

    val animatable = remember(endItems) { Animatable(0f) }

    val chartItems = when {
        endItems == null -> startItems
        animatable.value < 1f -> animatable.itemsTransition(startItems, endItems)
        else -> endItems
    }

    Row(
        modifier = Modifier.padding(20.dp)
    ) {
        ItemsList(
            chartItems = listItems,
            modifier = Modifier
                .weight(ListWeight)
                .fillMaxHeight()
        )

        Chart(
            chartItems = chartItems,
            modifier = Modifier
                .weight(ChartWeight)
                .fillMaxHeight()
                .onPointerEvent(PointerEventType.Move) { pointerEvent ->
                    if (!animatable.isRunning) {
                        val position = pointerEvent.changes.first().position - size.center.toOffset()
                        viewModel.onChartPositionHovered(position)
                    }
                }
                .onPointerEvent(PointerEventType.Press) { pointerEvent ->
                    if (!animatable.isRunning) {
                        val position = pointerEvent.changes.first().position - size.center.toOffset()
                        viewModel.onChartPositionClicked(position)
                    }
                }
        )
    }

    LaunchedEffect(endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDurationMillis)
        )
    }
}

private fun Animatable<Float, AnimationVector1D>.itemsTransition(
    fromItems: List<ChartItem>,
    toItems: List<ChartItem>
) = fromItems
    .zip(toItems)
    .map { (fromItem, toItem) ->
        ChartItem(
            diskEntry = fromItem.diskEntry,
            arc = arcTransition(fromItem.arc, toItem.arc),
            color = colorTransition(fromItem.color, toItem.color)
        )
    }

private fun Animatable<Float, AnimationVector1D>.arcTransition(
    fromArc: Arc,
    toArc: Arc
) = Arc(
    angleRange = rangeTransition(fromArc.angleRange, toArc.angleRange),
    radiusRange = rangeTransition(fromArc.radiusRange, toArc.radiusRange)
)

private fun Animatable<Float, AnimationVector1D>.colorTransition(
    fromColor: Color,
    toColor: Color
) = Color(
    red = valueTransition(fromColor.red, toColor.red),
    green = valueTransition(fromColor.green, toColor.green),
    blue = valueTransition(fromColor.blue, toColor.blue),
    alpha = valueTransition(fromColor.alpha, toColor.alpha)
)

private fun Animatable<Float, AnimationVector1D>.rangeTransition(
    fromRange: HalfOpenFloatRange,
    toRange: HalfOpenFloatRange
) = valueTransition(fromRange.start, toRange.start) until valueTransition(fromRange.end, toRange.end)

private fun Animatable<Float, AnimationVector1D>.valueTransition(
    fromValue: Float,
    toValue: Float
) = fromValue + (toValue - fromValue) * value
