package com.diskusage.presentation.screens.chart.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.list.ListItem
import com.diskusage.domain.model.path.PathInfo
import com.diskusage.libraries.formatters.FileSizeFormatter
import kotlin.io.path.name

@Composable
fun ItemHeader(
    listItem: ListItem,
    diskName: String? = null,
    modifier: Modifier = Modifier
) {
    Item(
        icon = {
            Icon(
                imageVector = when (listItem.pathInfo) {
                    is PathInfo.Directory -> Icons.Outlined.Folder
                    is PathInfo.File -> Icons.Outlined.Article
                },
                contentDescription = null,
                tint = listItem.color,
                modifier = Modifier.fillMaxSize()
            )
        },
        name = {
            Text(
                text = diskName ?: listItem.path.name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = FileSizeFormatter.toSiFormat(listItem.pathInfo.sizeOnDisk),
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium,
            )
        },
        modifier = modifier
    )
}

@Composable
fun ItemRow(
    listItem: ListItem,
    modifier: Modifier = Modifier
) {
    Item(
        icon = {
            Icon(
                imageVector = when (listItem.pathInfo) {
                    is PathInfo.Directory -> Icons.Outlined.Folder
                    is PathInfo.File -> Icons.Outlined.Article
                },
                contentDescription = null,
                tint = listItem.color
            )
        },
        name = {
            Text(
                text = listItem.path.name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = FileSizeFormatter.toSiFormat(listItem.pathInfo.sizeOnDisk),
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2
            )
        },
        modifier = modifier
    )
}

@Composable
private fun Item(
    icon: @Composable BoxScope.() -> Unit,
    name: @Composable RowScope.() -> Unit,
    description: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            horizontal = 8.dp,
            vertical = 4.dp
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            icon()
        }

        Spacer(Modifier.width(8.dp))

        name()

        Spacer(Modifier.width(8.dp))

        description()
    }
}
