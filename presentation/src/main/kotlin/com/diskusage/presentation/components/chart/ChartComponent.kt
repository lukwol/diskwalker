package com.diskusage.presentation.components.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.libraries.ranges.HalfOpenFloatRange
import com.diskusage.libraries.ranges.until
import com.diskusage.presentation.components.chart.blocks.Chart
import com.diskusage.presentation.components.chart.blocks.ItemRow
import com.diskusage.presentation.di.ViewModelProvider

private const val ChartWeight = 2f
private const val ListWeight = 1f

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChartComponent(diskEntry: DiskEntry) {
    val viewModel = remember { ViewModelProvider.getChartViewModel(diskEntry) }
    val viewState by viewModel.viewState.collectAsState()

    val listItemsCollection = viewState.listItemsCollection
    val chartItemsCollection = viewState.chartItemsCollection

    val animatable = remember(chartItemsCollection?.endItems) { Animatable(0f) }

    LaunchedEffect(chartItemsCollection?.endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDurationMillis)
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        if (listItemsCollection != null) {
            Column(
                Modifier
                    .weight(ListWeight)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                val (selectedItem, childItems) = listItemsCollection

                (listOf(selectedItem) + childItems).forEach { listItem ->
                    ItemRow(
                        listItem = listItem,
                        modifier = Modifier.clickable(
                            enabled = !animatable.isRunning &&
                                listItem.diskEntry.type == DiskEntry.Type.Directory &&
                                listItem.diskEntry.sizeOnDisk > 0,
                            onClick = { viewModel.onSelectListItem(listItem) }
                        )
                    )
                }
            }
        }
        if (chartItemsCollection != null) {
            val (startChartItems, endChartItems) = chartItemsCollection
            val chartItems = when {
                endChartItems == null -> startChartItems
                animatable.value < 1f -> animatable.itemsTransition(startChartItems, endChartItems)
                else -> endChartItems
            }

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
