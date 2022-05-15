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
import com.diskusage.presentation.common.components.FileDialog
import com.diskusage.presentation.common.components.FileDialogMode
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.screens.chart.components.Chart

@Composable
fun FrameWindowScope.ChartScreen(
    isSupportLibraryLoaded: Boolean,
) {
    val viewModel = remember { ViewModelProvider.chartViewModel }
    val viewState by viewModel.viewState.collectAsState()

    var showFileDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color(0xFF363736))
            .fillMaxSize()
    ) {
        Button(
            enabled = isSupportLibraryLoaded,
            onClick = {
                showFileDialog = true
            }
        ) {
            Text("Select directory")
        }

        if (viewState.startItems.isNotEmpty()) {
            Chart(
                startItems = viewState.startItems,
                endItems = viewState.endItems
            ) {
                println("Selected ${it.diskEntry.name}")
                viewModel.selectDiskEntry(it)
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
