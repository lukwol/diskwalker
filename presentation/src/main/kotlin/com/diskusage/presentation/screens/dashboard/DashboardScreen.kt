package com.diskusage.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diskusage.domain.common.Constants
import com.diskusage.presentation.navigation.AppRoutes
import com.diskusage.presentation.screens.dashboard.components.DiskButton
import io.github.anvell.async.Success
import io.github.anvell.async.Uninitialized
import io.github.lukwol.screens.navigation.LocalScreensController
import java.nio.file.Path

@Composable
fun DashboardScreen(
    state: DashboardViewState,
    commands: (DashboardCommand) -> Unit
) {
    val screensController = LocalScreensController.current

    val selectedDiskEntry = state.selectedDiskEntry

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        DiskButton(
            title = "Macintosh HD",
            progress = 0f,
            enabled = state.selectedDiskEntry == Uninitialized
        ) {
            commands(SelectScannedPath(Path.of(Constants.Disk.RootDiskPath)))
        }
    }

    LaunchedEffect(selectedDiskEntry) {
        if (selectedDiskEntry is Success) {
            screensController.push(AppRoutes.ChartScreen, selectedDiskEntry.value)
        }
    }
}
