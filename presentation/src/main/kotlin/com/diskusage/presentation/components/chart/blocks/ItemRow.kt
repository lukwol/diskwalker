package com.diskusage.presentation.components.chart.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
    ItemRow(
        item = item,
        textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
        modifier = modifier
    )
}

@Composable
fun ItemRow(
    item: ChartItem,
    textStyle: TextStyle = MaterialTheme.typography.subtitle2,
    modifier: Modifier = Modifier
) {
    val type = item.diskEntry.type
    val name = item.diskEntry.name
    val color = item.color
    val sizeOnDisk = item.diskEntry.sizeOnDisk

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(10.dp)
    ) {
        Icon(
            imageVector = when (type) {
                DiskEntry.Type.Directory -> Icons.Outlined.Folder
                DiskEntry.Type.File -> Icons.Outlined.Article
            },
            contentDescription = type.name,
            tint = color
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = name,
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            style = textStyle,
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = humanReadableSize(sizeOnDisk.toDouble()),
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            style = textStyle
        )
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
private fun Preview() {
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
        ),
        textStyle = MaterialTheme.typography.body2
    )
}
