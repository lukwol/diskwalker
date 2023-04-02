package com.diskusage.presentation.screens.chart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.list.ListItem
import com.diskusage.libraries.formatters.FileSizeFormatter
import com.diskusage.presentation.components.Label
import kotlin.io.path.pathString

@Composable
fun ItemRow(
    listItem: ListItem,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    iconScale: Float = 1f,
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
            Icon(
                imageVector = when (listItem.pathInfo.isFile) {
                    true -> Icons.Outlined.Article
                    false -> Icons.Outlined.Folder
                },
                contentDescription = null,
                tint = listItem.color,
                modifier = Modifier.scale(iconScale),
            )
        }

        Spacer(Modifier.width(8.dp))

        Label(
            text = listItem.pathInfo.name.takeIf(String::isNotEmpty) ?: listItem.path.pathString,
            color = MaterialTheme.colors.onBackground,
            style = textStyle,
            overflow = TextOverflow.Clip,
            modifier = Modifier.weight(1f),
        )

        Spacer(Modifier.width(8.dp))

        Label(
            text = FileSizeFormatter.toSiFormat(listItem.pathInfo.sizeOnDisk),
            color = MaterialTheme.colors.onBackground,
            overflow = TextOverflow.Clip,
            style = textStyle,
        )
    }
}
