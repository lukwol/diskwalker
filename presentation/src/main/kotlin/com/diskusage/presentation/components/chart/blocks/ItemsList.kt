package com.diskusage.presentation.components.chart.blocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.diskusage.domain.model.ListItemsCollection

@Composable
fun ItemsList(
    listItemsCollection: ListItemsCollection,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        val (selectedItem, childItems, _) = listItemsCollection

        (listOf(selectedItem) + childItems).forEach { listItem ->
            Text(
                text = listItem.diskEntry.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(listItem.color)
            )
        }
    }
}
