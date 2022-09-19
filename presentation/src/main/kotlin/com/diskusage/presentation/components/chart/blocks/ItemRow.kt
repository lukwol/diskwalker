package com.diskusage.presentation.components.chart.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.Arc
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.model.DiskEntry
import com.diskusage.libraries.ranges.until
import java.nio.file.Path

@Composable
fun ItemHeader(
    item: ChartItem,
    modifier: Modifier = Modifier
) {
    val type = item.diskEntry.type
    val name = item.diskEntry.name
    val color = item.color
    val sizeOnDisk = item.diskEntry.sizeOnDisk

    Item(
        icon = {
            Icon(
                imageVector = when (type) {
                    DiskEntry.Type.Directory -> Icons.Default.Folder
                    DiskEntry.Type.File -> Icons.Default.Article
                },
                contentDescription = type.name,
                tint = color,
                modifier = Modifier.fillMaxSize()
            )
        },
        name = {
            Text(
                text = name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = humanReadableSize(sizeOnDisk.toDouble()),
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
    item: ChartItem,
    modifier: Modifier = Modifier
) {
    val type = item.diskEntry.type
    val name = item.diskEntry.name
    val color = item.color
    val sizeOnDisk = item.diskEntry.sizeOnDisk

    Item(
        icon = {
            Icon(
                imageVector = when (type) {
                    DiskEntry.Type.Directory -> Icons.Default.Folder
                    DiskEntry.Type.File -> Icons.Default.Article
                },
                contentDescription = type.name,
                tint = color
            )
        },
        name = {
            Text(
                text = name,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = humanReadableSize(sizeOnDisk.toDouble()),
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
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(32.dp)
        ) {
            icon()
        }

        Spacer(Modifier.width(10.dp))

        name()

        Spacer(Modifier.width(10.dp))

        description()
    }
}

// TODO: Move to usecase
private fun humanReadableSize(bytes: Double) = when {
    bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30))
    bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20))
    bytes >= 1 shl 10 -> "%.0f kB".format(bytes / (1 shl 10))
    else -> "${bytes.toInt()} bytes"
}

@Preview
@Composable
private fun ItemHeaderPreview() {
    ItemRow(
        item = ChartItem(
            diskEntry = DiskEntry(
                name = "dir",
                type = DiskEntry.Type.Directory,
                path = Path.of("/dir"),
                parent = null,
                sizeOnDisk = 12800
            ),
            arc = Arc(
                angleRange = 0f until 360f,
                radiusRange = 0f until 360f
            ),
            color = Color.Magenta
        )
    )
}

@Preview
@Composable
private fun ItemRowPreview() {
    ItemRow(
        item = ChartItem(
            diskEntry = DiskEntry(
                name = "file",
                type = DiskEntry.Type.File,
                path = Path.of("/file"),
                parent = null,
                sizeOnDisk = 8192
            ),
            arc = Arc(
                angleRange = 0f until 360f,
                radiusRange = 0f until 360f
            ),
            color = Color.Cyan
        )
    )
}
