package com.diskwalker.presentation.screens.scan.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            .size(360.dp)
            .aspectRatio(1f),
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .matchParentSize()
                .padding(20.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .padding(40.dp),
            ) {
                Icon(
                    painter = painterResource("images/hard-drive-outlined.svg"),
                    contentDescription = diskName,
                    modifier = Modifier
                        .padding(20.dp)
                        .matchParentSize(),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.BottomCenter),
                ) {
                    Label(
                        text = diskName,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Medium,
                    )

                    Label(
                        text = "$takenDiskSpace / $totalDiskSize",
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }

        CircularProgressIndicator(
            progress = animateFloatAsState(progress).value,
            strokeWidth = 6.dp,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskScanProgress(
        diskName = "Macintosh HD",
        takenDiskSpace = "2023.87 GB",
        totalDiskSize = "4942.34 GB",
        progress = 0.7f,
    )
}
