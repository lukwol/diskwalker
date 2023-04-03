@file:Suppress("ConstPropertyName")

package com.diskwalker.presentation.screens.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.West
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.diskwalker.domain.common.Constants.Chart.AnimationDurationMillis
import com.diskwalker.domain.model.chart.Arc
import com.diskwalker.domain.model.chart.ChartItem
import com.diskwalker.libraries.ranges.HalfOpenFloatRange
import com.diskwalker.libraries.ranges.until
import com.diskwalker.presentation.components.ActionButton
import com.diskwalker.presentation.navigation.AppRoutes
import com.diskwalker.presentation.screens.chart.components.Chart
import com.diskwalker.presentation.screens.chart.components.ItemRow
import io.github.lukwol.screens.navigation.LocalScreensController

private const val ChartWeight = 2f
private const val ListWeight = 1f

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChartScreen(
    state: ChartViewState,
    commands: (ChartCommand) -> Unit,
) {
    val screensController = LocalScreensController.current

    val listData = state.listData
    val chartData = state.chartData

    val lazyListState = rememberLazyListState()

    val animatable = remember(chartData?.endItems) { Animatable(0f) }

    LaunchedEffect(chartData?.endItems) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = AnimationDurationMillis),
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        if (listData != null) {
            val (selectedItem, childItems) = listData

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(ListWeight),
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .padding(end = 10.dp),
                    ) {
                        stickyHeader {
                            ItemRow(
                                listItem = selectedItem,
                                textStyle = MaterialTheme.typography.subtitle1.copy(
                                    fontWeight = FontWeight.Medium,
                                ),
                                iconScale = 1.3f,
                                modifier = Modifier
                                    .clickable(
                                        enabled = !animatable.isRunning,
                                        onClick = { commands(OnSelectPath(selectedItem.path)) },
                                    )
                                    .background(MaterialTheme.colors.background),
                            )
                        }

                        items(childItems) { item ->
                            ItemRow(
                                listItem = item,
                                textStyle = MaterialTheme.typography.subtitle2,
                                modifier = Modifier.clickable(
                                    enabled = !animatable.isRunning &&
                                        !item.pathInfo.isFile &&
                                        item.pathInfo.sizeOnDisk > 0L,
                                    onClick = { commands(OnSelectPath(item.path)) },
                                ),
                            )
                        }
                    }

                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(lazyListState),
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                    )
                }

                ActionButton(
                    title = "Select Disk",
                    icon = Icons.Outlined.West,
                    onClick = { screensController.pop(AppRoutes.DashboardScreen) },
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(end = defaultScrollbarStyle().thickness),
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
                    },
            )
        }
    }
}

private fun Animatable<Float, AnimationVector1D>.itemsTransition(
    fromItems: List<ChartItem>,
    toItems: List<ChartItem>,
) = fromItems
    .zip(toItems)
    .map { (fromItem, toItem) ->
        ChartItem(
            path = fromItem.path,
            arc = arcTransition(fromItem.arc, toItem.arc),
            color = colorTransition(fromItem.color, toItem.color),
        )
    }

private fun Animatable<Float, AnimationVector1D>.arcTransition(
    fromArc: Arc,
    toArc: Arc,
) = Arc(
    angleRange = rangeTransition(fromArc.angleRange, toArc.angleRange),
    radiusRange = rangeTransition(fromArc.radiusRange, toArc.radiusRange),
)

private fun Animatable<Float, AnimationVector1D>.colorTransition(
    fromColor: Color,
    toColor: Color,
) = Color(
    red = valueTransition(fromColor.red, toColor.red),
    green = valueTransition(fromColor.green, toColor.green),
    blue = valueTransition(fromColor.blue, toColor.blue),
    alpha = valueTransition(fromColor.alpha, toColor.alpha),
)

private fun Animatable<Float, AnimationVector1D>.rangeTransition(
    fromRange: HalfOpenFloatRange,
    toRange: HalfOpenFloatRange,
) = valueTransition(fromRange.start, toRange.start) until valueTransition(fromRange.end, toRange.end)

private fun Animatable<Float, AnimationVector1D>.valueTransition(
    fromValue: Float,
    toValue: Float,
) = fromValue + (toValue - fromValue) * value
