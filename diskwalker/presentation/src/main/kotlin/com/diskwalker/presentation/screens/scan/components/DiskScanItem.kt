package com.diskwalker.presentation.screens.scan.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskwalker.presentation.components.Label
import com.diskwalker.presentation.preview.PreviewEnvironment

@Composable
fun DiskScanProgress(
    diskName: String,
    takenDiskSpace: String,
    totalDiskSize: String,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
) {
    Box(
        modifier = modifier
            .size(300.dp)
            .aspectRatio(1f),
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .matchParentSize()
                .padding(24.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .align(Alignment.Center),
                ) {
                    Label(
                        text = diskName,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                    )

                    Label(
                        text = "$takenDiskSpace / $totalDiskSize",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        CircularProgressIndicator(
            progress = animateFloatAsState(progress).value,
            strokeWidth = 8.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskScanProgress(
        diskName = "Macintosh HD",
        takenDiskSpace = "223.87 GB",
        totalDiskSize = "442.34 GB",
        progress = 0.7f,
    )
}
