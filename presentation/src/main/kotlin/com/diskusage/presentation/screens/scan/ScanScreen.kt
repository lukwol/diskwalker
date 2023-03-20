package com.diskusage.presentation.screens.scan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diskusage.libraries.formatters.FileSizeFormatter
import com.diskusage.presentation.navigation.AppRoutes
import com.diskusage.presentation.screens.scan.components.DiskButton
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
import io.github.lukwol.screens.navigation.LocalScreensController

@Composable
fun ScanScreen(
    state: ScanViewState,
    commands: (ScanCommand) -> Unit,
) {
    val screensController = LocalScreensController.current

    val diskInfo = state.diskInfo
    val scanState = state.scanState

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        DiskButton(
            diskName = diskInfo.name,
            takenDiskSpace = diskInfo.takenSpace.let(FileSizeFormatter::toSiFormat),
            totalDiskSize = diskInfo.totalSpace.let(FileSizeFormatter::toSiFormat),
            progress = when (scanState) {
                is Loading -> scanState.progress ?: 0f
                is Success -> 1f
                else -> 0f
            },
        )
    }

    LaunchedEffect(scanState) {
        if (scanState is Success) {
            screensController.push(AppRoutes.ChartScreen, diskInfo.mountPoint)
        }
    }
}
