package com.diskusage.presentation.screens.scanresult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.screens.chart.ChartComponent
import java.nio.file.Path

@Composable
fun ScanResultComponent(
    isSupportLibraryLoaded: Boolean
) {
    val viewModel = remember { ViewModelProvider.getScanResultViewModel() }
    val viewState by viewModel.viewState.collectAsState()

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
                onClick = {
                    viewModel.selectScannedPath(Path.of("/"))
                }
            ) {
                Text("Scan disk")
            }
        }
    }
}
