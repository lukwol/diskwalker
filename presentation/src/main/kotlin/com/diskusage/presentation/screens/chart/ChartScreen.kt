package com.diskusage.presentation.screens.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.diskusage.presentation.screens.chart.components.Chart
import com.diskusage.presentation.screens.chart.components.ItemHeader
import com.diskusage.presentation.screens.chart.components.ItemRow

private const val ChartWeight = 2f
private const val ListWeight = 1f

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChartScreen(
    state: ChartViewState,
    commands: (ChartCommand) -> Unit
) {
    val listData = state.listData
    val chartData = state.chartData

    val lazyListState = rememberLazyListState()

    val animatable = remember(chartData?.endItems) { Animatable(0f) }

    LaunchedEffect(chartData?.endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDurationMillis)
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (listData != null) {
            val (selectedItem, childItems) = listData

            Box(
                modifier = Modifier.weight(ListWeight)
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 10.dp)
                ) {
                    stickyHeader {
                        ItemHeader(
                            listItem = selectedItem,
                            modifier = Modifier
                                .clickable(
                                    enabled = !animatable.isRunning && selectedItem.diskEntry.parent != null,
                                    onClick = { commands(OnSelectDiskEntry(selectedItem.diskEntry)) }
                                )
                                .background(MaterialTheme.colors.background)
                        )
                    }

                    items(childItems) { item ->
                        ItemRow(
                            listItem = item,
                            modifier = Modifier.clickable(
                                enabled = !animatable.isRunning &&
                                    item.diskEntry.type == DiskEntry.Type.Directory &&
                                    item.diskEntry.sizeOnDisk > 0L,
                                onClick = { commands(OnSelectDiskEntry(item.diskEntry)) }
                            )
                        )
                    }
                }

                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(lazyListState),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                )
            }
        }
        if (chartData != null) {
            val (startChartItems, endChartItems) = chartData
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
                            commands(OnChartPositionHovered(position))
                        }
                    }
                    .onPointerEvent(PointerEventType.Press) { pointerEvent ->
                        if (!animatable.isRunning) {
                            val position = pointerEvent.changes.first().position - size.center.toOffset()
                            commands(OnChartPositionClicked(position))
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
