import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.FrameWindowScope
import com.diskusage.presentation.common.blocks.FileDialog
import com.diskusage.presentation.common.blocks.FileDialogMode
import com.diskusage.presentation.components.chart.ChartComponent
import com.diskusage.presentation.di.ViewModelProvider

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
            .background(color = Color(0xFF363736))
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

        if (showFileDialog) {
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
