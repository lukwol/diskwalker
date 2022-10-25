package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.*
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

internal data class WindowRoute(
    val window: String,
    val route: NavRoute
)

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> NavMapBuilder.composable(
    route: NavRoute,
    viewModelFactory: (args: NavArguments?) -> VM,
    content: @Composable (viewModel: VM) -> Unit
) {
    composable(route) { arguments ->
        val viewModelStore = LocalViewModelStore.current
        val navController = LocalNavController.current
        val windowsController = LocalWindowController.current

        val windowRoute = WindowRoute(windowTitle, route)

        val viewModel = remember(route) {
            viewModelStore.viewModels.getOrPut(windowRoute) { viewModelFactory(arguments) } as VM
        }

        content(viewModel)

        DisposableEffect(route) {
            onDispose {
                viewModel.viewModelScope.cancel()
                if (windowTitle !in windowsController.windows) {
                    viewModelStore.viewModels.keys.filter {
                        it.window == windowTitle
                    }.forEach {
                        viewModelStore.viewModels.remove(it)
                    }
                } else if (route !in navController.routes) {
                    viewModelStore.viewModels.remove(windowRoute)
                }
                println("viewModelStore = $viewModelStore, viewModels = ${viewModelStore.viewModels.keys}")
            }
        }
    }
}
