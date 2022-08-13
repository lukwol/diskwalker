package com.diskusage.presentation.components.chart.blocks

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diskusage.domain.entities.ChartItem

@Composable
fun ItemsList(
    chartItems: List<ChartItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Items List")
    }
}
