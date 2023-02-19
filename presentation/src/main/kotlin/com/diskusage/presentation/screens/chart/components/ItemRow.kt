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
import com.diskusage.domain.model.ListItem
import com.diskusage.domain.model.scan.ScanItem
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
                imageVector = when (listItem.scanItem) {
                    is ScanItem.Directory -> Icons.Outlined.Folder
                    is ScanItem.File -> Icons.Outlined.Article
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
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.weight(1f)
            )
        },
        description = {
            Text(
                text = FileSizeFormatter.toSiFormat(listItem.scanItem.sizeOnDisk),
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
                imageVector = when (listItem.scanItem) {
                    is ScanItem.Directory -> Icons.Outlined.Folder
                    is ScanItem.File -> Icons.Outlined.Article
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
                text = FileSizeFormatter.toSiFormat(listItem.scanItem.sizeOnDisk),
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

// @Preview
// @Composable
// private fun ItemHeaderPreview() {
//    ItemHeader(
//        listItem = ListItem(
//            diskEntry = DiskEntry(
//                name = "dir",
//                type = DiskEntry.Type.Directory,
//                path = Path.of("/dir"),
//                parent = null,
//                sizeOnDisk = 12800
//            ),
//            color = Color.Cyan
//        )
//    )
// }
//
// @Preview
// @Composable
// private fun ItemRowPreview() {
//    ItemRow(
//        ListItem(
//            diskEntry = DiskEntry(
//                name = "file1",
//                type = DiskEntry.Type.File,
//                path = Path.of("/dir/file1"),
//                parent = null,
//                sizeOnDisk = 5120
//            ),
//            color = Color.Magenta
//        )
//    )
// }
