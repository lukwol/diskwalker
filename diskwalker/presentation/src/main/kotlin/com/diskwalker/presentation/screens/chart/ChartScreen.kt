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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.West
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.diskwalker.domain.common.Constants.Chart.AnimationDurationMillis
import com.diskwalker.domain.model.chart.Arc
import com.diskwalker.domain.model.chart.ChartItem
import com.diskwalker.libraries.ranges.HalfOpenFloatRange
import com.diskwalker.libraries.ranges.until
import com.diskwalker.presentation.components.ActionButton
import com.diskwalker.presentation.navigation.AppRoutes
import com.diskwalker.presentation.screens.chart.components.Chart
import com.diskwalker.presentation.screens.chart.components.ItemRow
import io.github.lukwol.cmnav.screens.LocalScreensController

private const val ChartWeight = 2f
private const val ListWeight = 1f

@OptIn(ExperimentalFoundationApi::class)
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
            .padding(24.dp),
    ) {
        if (listData != null) {
            val (selectedItem, childItems) = listData

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(ListWeight),
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    Card(
                        modifier = Modifier
                            .padding(end = 10.dp),
                    ) {
                        LazyColumn(
                            state = lazyListState,
                        ) {
                            stickyHeader {
                                ItemRow(
                                    listItem = selectedItem,
                                    textStyle = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                    ),
                                    iconScale = 1.3f,
                                    modifier = Modifier
                                        .clickable(
                                            enabled = !animatable.isRunning,
                                            onClick = { commands(OnSelectPath(selectedItem.path)) },
                                        )
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant
                                                .copy(alpha = 0.9f)
                                                .compositeOver(MaterialTheme.colorScheme.onBackground),
                                        ),
                                )
                            }

                            items(childItems) { item ->
                                ItemRow(
                                    listItem = item,
                                    textStyle = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .clickable(
                                            enabled = !animatable.isRunning &&
                                                !item.pathInfo.isFile &&
                                                item.pathInfo.sizeOnDisk > 0L,
                                            onClick = { commands(OnSelectPath(item.path)) },
                                        ),
                                )
                            }
                        }
                    }
                    VerticalScrollbar(
                        adapter = rememberScrollbarAdapter(lazyListState),
                        style = defaultScrollbarStyle().copy(
                            unhoverColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
                            hoverColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50f),
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                    )
                }

                ActionButton(
                    title = "Select Disk",
                    icon = Icons.Outlined.West,
                    onClick = { screensController.pop(AppRoutes.DashboardScreen) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
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
                onPointerMove = { position ->
                    if (!animatable.isRunning) {
                        commands(OnChartPositionHovered(position))
                    }
                },
                onPointerPress = { position ->
                    if (!animatable.isRunning) {
                        commands(OnChartPositionClicked(position))
                    }
                },
                modifier = Modifier.weight(ChartWeight),
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
