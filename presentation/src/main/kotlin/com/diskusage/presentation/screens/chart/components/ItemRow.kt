package com.diskusage.presentation.screens.chart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.list.ListItem
import com.diskusage.libraries.formatters.FileSizeFormatter

@Composable
fun ItemRow(
    listItem: ListItem,
    modifier: Modifier = Modifier,
) {
    Item(
        icon = {
            Icon(
                imageVector = when (listItem.pathInfo.isFile) {
                    true -> Icons.Outlined.Article
                    false -> Icons.Outlined.Folder
                },
                contentDescription = null,
                tint = listItem.color,
            )
        },
        name = {
            Text(
                text = listItem.pathInfo.name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f),
            )
        },
        description = {
            Text(
                text = FileSizeFormatter.toSiFormat(listItem.pathInfo.sizeOnDisk),
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2,
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun Item(
    icon: @Composable BoxScope.() -> Unit,
    name: @Composable RowScope.() -> Unit,
    description: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            horizontal = 8.dp,
            vertical = 4.dp,
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp),
        ) {
            icon()
        }

        Spacer(Modifier.width(8.dp))

        name()

        Spacer(Modifier.width(8.dp))

        description()
    }
}
