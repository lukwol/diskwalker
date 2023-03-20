package com.diskusage.presentation.screens.dashboard

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.system.SystemInfo
import com.diskusage.presentation.preview.PreviewDataProvider
import com.diskusage.presentation.preview.PreviewEnvironment
import com.diskusage.presentation.screens.dashboard.components.DiskRow

@Composable
fun DashboardScreen(
    state: DashboardViewState,
    commands: (DashboardCommand) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(20.dp),
    ) {
        for (diskInfo in state.systemInfo.disks) {
            SelectionContainer {
                DiskRow(diskInfo = diskInfo)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = PreviewEnvironment {
    DashboardScreen(
        state = DashboardViewState(
            systemInfo = SystemInfo(
                disks = PreviewDataProvider.DiskInfo.allDisksInfo,
            ),
        ),
        commands = {},
    )
}
