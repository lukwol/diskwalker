package com.diskwalker.presentation.screens.scan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diskwalker.libraries.formatters.FileSizeFormatter
import com.diskwalker.presentation.components.ActionButton
import com.diskwalker.presentation.navigation.AppRoutes
import com.diskwalker.presentation.screens.scan.components.DiskScanProgress
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
import io.github.lukwol.cmnav.screens.LocalScreensController
import kotlin.io.path.pathString

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
        DiskScanProgress(
            diskName = diskInfo.run { name.ifEmpty(mountPoint::pathString) },
            takenDiskSpace = diskInfo.takenSpace.let(FileSizeFormatter::toSiFormat),
            totalDiskSize = diskInfo.totalSpace.let(FileSizeFormatter::toSiFormat),
            progress = when (scanState) {
                is Loading -> scanState.progress ?: 0f
                is Success -> 1f
                else -> 0f
            },
        )

        ActionButton(
            title = "Cancel",
            icon = Icons.Outlined.Close,
            onClick = screensController::pop,
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter),
        )
    }

    LaunchedEffect(scanState) {
        if (scanState is Success) {
            screensController.push(AppRoutes.ChartScreen, diskInfo.mountPoint)
        }
    }
}
