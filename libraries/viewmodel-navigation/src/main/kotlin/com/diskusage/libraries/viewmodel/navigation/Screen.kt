package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.*
import com.diskusage.libraries.navigation.screens.*
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> ScreensMapBuilder.screen(
    route: ScreenRoute,
    viewModelFactory: (args: Arguments?) -> VM,
    content: @Composable (viewModel: VM) -> Unit
) {
    screen(route) { arguments ->
        val screensController = LocalScreensController.current

        val viewModelStore = remember { ViewModelStore() }
        val viewModel = remember(route) {
            viewModelStore.getOrPut(route) { viewModelFactory(arguments) } as VM
        }

        content(viewModel)

        DisposableEffect(route) {
            onDispose {
                viewModel.viewModelScope.cancel()
                if (route !in screensController.routes) {
                    viewModelStore.remove(route)
                }
            }
        }
    }
}
