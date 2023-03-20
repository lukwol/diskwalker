package com.diskusage.presentation.screens.dashboard.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.disk.DiskInfo
import com.diskusage.presentation.preview.PreviewDataProvider
import com.diskusage.presentation.preview.PreviewEnvironment

@Composable
fun DiskRow(diskInfo: DiskInfo) {
    Card {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(text = diskInfo.name)
        }
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DiskRow(diskInfo = PreviewDataProvider.DiskInfo.macintoshHd)
}
