package com.diskusage.presentation.components.chart.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ItemHeader(
    name: String,
    description: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Item(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
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
                text = description,
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
    name: String,
    description: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Item(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
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
                text = description,
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

@Preview
@Composable
private fun ItemHeaderPreview() {
    ItemHeader(
        name = "Folder 1",
        description = "24 MB",
        icon = Icons.Outlined.Folder,
        color = Color.Cyan
    )
}

@Preview
@Composable
private fun ItemRowPreview() {
    ItemRow(
        name = "File 1",
        description = "42 kB",
        icon = Icons.Outlined.Article,
        color = Color.Magenta
    )
}
