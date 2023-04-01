package com.diskusage.presentation.screens.dashboard

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskusage.domain.model.system.SystemInfo
import com.diskusage.presentation.components.ActionButton
import com.diskusage.presentation.navigation.AppRoutes
import com.diskusage.presentation.preview.PreviewDataProvider
import com.diskusage.presentation.preview.PreviewEnvironment
import com.diskusage.presentation.screens.dashboard.components.DiskRow
import io.github.lukwol.screens.navigation.LocalScreensController

@Composable
fun DashboardScreen(
    state: DashboardViewState,
    commands: (DashboardCommand) -> Unit,
) {
    val screensController = LocalScreensController.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(20.dp),
        ) {
            for (diskInfo in state.systemInfo.disks) {
                DiskRow(
                    diskInfo = diskInfo,
                    onScanClicked = {
                        screensController.push(
                            route = AppRoutes.ScanScreen,
                            arguments = diskInfo,
                        )
                    },
                )
            }
        }

        ActionButton(
            title = "Reload Disks",
            icon = Icons.Outlined.Refresh,
            onClick = { commands(DashboardCommand.ReloadDiskInfo) },
            backgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
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
