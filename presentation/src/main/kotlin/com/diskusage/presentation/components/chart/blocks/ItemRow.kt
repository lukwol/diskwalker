package com.diskusage.presentation.components.chart.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.model.ListItem
import java.nio.file.Path

@Composable
fun ItemHeader(
    listItem: ListItem,
    modifier: Modifier = Modifier
) {
    Item(
        icon = {
            Icon(
                imageVector = when (listItem.diskEntry.type) {
                    DiskEntry.Type.Directory -> Icons.Outlined.Folder
                    DiskEntry.Type.File -> Icons.Outlined.Article
                },
                contentDescription = null,
                tint = listItem.color,
                modifier = Modifier.fillMaxSize()
            )
        },
        name = {
            Text(
                text = listItem.diskEntry.name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = humanReadableSize(listItem.diskEntry.sizeOnDisk.toDouble()),
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
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
                imageVector = when (listItem.diskEntry.type) {
                    DiskEntry.Type.Directory -> Icons.Outlined.Folder
                    DiskEntry.Type.File -> Icons.Outlined.Article
                },
                contentDescription = null,
                tint = listItem.color
            )
        },
        name = {
            Text(
                text = listItem.diskEntry.name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = humanReadableSize(listItem.diskEntry.sizeOnDisk.toDouble()),
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
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(
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

// TODO: Move to library
private fun humanReadableSize(bytes: Double) = when {
    bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30))
    bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20))
    bytes >= 1 shl 10 -> "%.0f kB".format(bytes / (1 shl 10))
    else -> "${bytes.toInt()} bytes"
}

@Preview
@Composable
private fun ItemHeaderPreview() {
    ItemHeader(
        listItem = ListItem(
            diskEntry = DiskEntry(
                name = "dir",
                type = DiskEntry.Type.Directory,
                path = Path.of("/dir"),
                parent = null,
                sizeOnDisk = 12800
            ),
            color = Color.Cyan
        )
    )
}

@Preview
@Composable
private fun ItemRowPreview() {
    ItemRow(
        ListItem(
            diskEntry = DiskEntry(
                name = "file1",
                type = DiskEntry.Type.File,
                path = Path.of("/dir/file1"),
                parent = null,
                sizeOnDisk = 5120
            ),
            color = Color.Magenta
        )
    )
}
