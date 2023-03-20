package com.diskusage.presentation.screens.dashboard.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.disk.DiskInfo
import com.diskusage.libraries.formatters.FileSizeFormatter
import com.diskusage.presentation.preview.PreviewDataProvider
import com.diskusage.presentation.preview.PreviewEnvironment
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
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                painter = painterResource("images/hard-drive-outlined.svg"),
                contentDescription = diskInfo.name,
            )

            Text(
                text = diskInfo.mountPoint.pathString,
                style = MaterialTheme.typography.subtitle2,
            )

            if (diskInfo.name.isNotEmpty()) {
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                ) {
                    Text(
                        text = diskInfo.name,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .padding(5.dp),
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
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small),
                )

                Text(
                    text = "$takenSpace used out of $totalSpace",
                    style = MaterialTheme.typography.caption,
                )
            }

            Button(
                onClick = onScanClicked,
            ) {
                Text(text = "Scan")
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
