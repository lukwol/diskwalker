package com.diskusage.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diskusage.domain.common.Constants
import com.diskusage.presentation.navigation.AppRoutes
import io.github.anvell.async.Success
import io.github.anvell.async.Uninitialized
import io.github.lukwol.screens.navigation.LocalScreensController
import java.nio.file.Path

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val screensController = LocalScreensController.current

    val viewState by viewModel.viewState.collectAsState()
    val selectedDiskEntry = viewState.selectedDiskEntry

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Button(
            enabled = viewState.selectedDiskEntry == Uninitialized,
            onClick = {
                viewModel.selectScannedPath(Path.of(Constants.Disk.RootDiskPath))
            }
        ) {
            Text("Scan disk")
        }
    }

    LaunchedEffect(selectedDiskEntry) {
        if (selectedDiskEntry is Success) {
            screensController.push(AppRoutes.ChartScreen, selectedDiskEntry())
        }
    }
}
