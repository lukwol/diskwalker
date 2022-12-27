package com.diskusage.presentation.screens.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.presentation.preview.PreviewEnvironment

@Composable
fun DiskButton(
    diskName: String,
    takenDiskSpace: String,
    totalDiskSize: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    progress: Float? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(310.dp)
            .aspectRatio(1f)
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .matchParentSize()
                .padding(20.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        enabled = enabled,
                        onClick = onClick
                    )
                    .offset(y = (-20).dp)
                    .padding(40.dp)
            ) {
                Icon(
                    painter = painterResource("hard-drive-outlined.svg"),
                    contentDescription = diskName,
                    modifier = Modifier
                        .padding(30.dp)
                        .matchParentSize()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = diskName,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Text(
                        text = "$takenDiskSpace / $totalDiskSize",
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }

        CircularProgressIndicator(
            progress = animateFloatAsState(progress ?: 0f).value,
            strokeWidth = 6.dp,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskButton(
        diskName = "Macintosh HD",
        takenDiskSpace = "203.87 GB",
        totalDiskSize = "494.34 GB",
        enabled = true,
        progress = 0.7f,
        onClick = {}
    )
}
