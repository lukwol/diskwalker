package com.diskusage.presentation.screens.scanresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.FrameWindowScope
import com.diskusage.presentation.components.FileDialog
import com.diskusage.presentation.components.FileDialogMode
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.screens.chart.ChartComponent

@Composable
fun FrameWindowScope.ScanResultComponent(
    isSupportLibraryLoaded: Boolean
) {
    val viewModel = remember { ViewModelProvider.getScanResultViewModel() }
    val viewState by viewModel.viewState.collectAsState()

    var showFileDialog by remember { mutableStateOf(false) }
    val selectedDiskEntry = viewState.scannedDiskEntry

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        if (selectedDiskEntry != null) {
            ChartComponent(diskEntry = selectedDiskEntry)
        } else {
            Button(
                enabled = isSupportLibraryLoaded,
                onClick = { showFileDialog = true }
            ) {
                Text("Select directory")
            }
        }

        if (showFileDialog && viewState.scannedDiskEntry == null) {
            FileDialog(
                title = "Choose a file",
                mode = FileDialogMode.Load,
                onResult = { path ->
                    if (path != null) {
                        viewModel.selectScannedPath(path)
                    }
                    showFileDialog = false
                }
            )
        }
    }
}
