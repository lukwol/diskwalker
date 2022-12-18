package com.diskusage.presentation.screens.dashboard.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.diskusage.presentation.preview.PreviewEnvironment

@Composable
fun DiskButton(
    title: String,
    progress: Float,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(360.dp)
            .aspectRatio(1f)
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .matchParentSize()
                .padding(30.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        enabled = enabled,
                        onClick = onClick
                    )
                    .padding(40.dp)
            ) {
                Icon(
                    painter = painterResource("hard-drive-outlined.svg"),
                    contentDescription = title,
                    modifier = Modifier
                        .padding(20.dp)
                        .matchParentSize()
                )

                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }

        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskButton(
        title = "Macintosh HD",
        progress = 0.7f,
        enabled = true,
        onClick = {}
    )
}
