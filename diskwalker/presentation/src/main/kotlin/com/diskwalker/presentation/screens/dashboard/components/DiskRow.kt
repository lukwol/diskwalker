package com.diskwalker.presentation.screens.dashboard.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SdCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.libraries.formatters.FileSizeFormatter
import com.diskwalker.presentation.components.Label
import com.diskwalker.presentation.preview.PreviewDataProvider
import com.diskwalker.presentation.preview.PreviewEnvironment
import kotlin.io.path.pathString

@Composable
fun DiskRow(
    diskInfo: DiskInfo,
    onScanClicked: () -> Unit,
) {
    val takenSpace = FileSizeFormatter.toSiFormat(diskInfo.takenSpace)
    val totalSpace = FileSizeFormatter.toSiFormat(diskInfo.totalSpace)

    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .widthIn(max = 1000.dp)
                .padding(10.dp),
        ) {
            if (diskInfo.isRemovable) {
                Icon(
                    imageVector = Icons.Outlined.SdCard,
                    contentDescription = diskInfo.name,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                )
            } else {
                Icon(
                    painter = painterResource("images/hard-drive-outlined.svg"),
                    contentDescription = diskInfo.name,
                    modifier = Modifier.size(48.dp),
                )
            }

            Label(
                text = diskInfo.mountPoint.pathString,
                style = MaterialTheme.typography.titleMedium,
            )

            if (diskInfo.name.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                ) {
                    Label(
                        text = diskInfo.name,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            ),
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                LinearProgressIndicator(
                    progress = diskInfo.takenSpace.toFloat() / diskInfo.totalSpace.toFloat(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f),
                    modifier = Modifier
                        .scale(scaleX = 1f, scaleY = 1.5f)
                        .clip(MaterialTheme.shapes.small),
                )

                Label(
                    text = "$takenSpace used out of $totalSpace",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Button(
                onClick = onScanClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Label(
                    text = "Scan",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskRow(
        diskInfo = PreviewDataProvider.DiskInfo.sony,
        onScanClicked = {},
    )
}
