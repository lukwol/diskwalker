package com.diskwalker.presentation.screens.dashboard

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskwalker.domain.model.system.SystemInfo
import com.diskwalker.presentation.components.ActionButton
import com.diskwalker.presentation.navigation.AppRoutes
import com.diskwalker.presentation.preview.PreviewDataProvider
import com.diskwalker.presentation.preview.PreviewEnvironment
import com.diskwalker.presentation.screens.dashboard.components.DiskRow
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
                            args = diskInfo,
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
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter),
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
