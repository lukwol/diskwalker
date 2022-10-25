package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.*
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

internal data class CombinedRoute(
    val windowRoute: String,
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

        val combinedRoute = CombinedRoute(windowRoute, route)

        val viewModel = remember(route) {
            viewModelStore.getOrPut(combinedRoute) { viewModelFactory(arguments) } as VM
        }

        content(viewModel)

        DisposableEffect(route) {
            onDispose {
                viewModel.viewModelScope.cancel()
                if (windowRoute !in windowsController.windowRoutes) {
                    viewModelStore.remove(windowRoute)
                } else if (route !in navController.routes) {
                    viewModelStore.remove(combinedRoute)
                }
                println("viewModelStore = $viewModelStore, viewModels = ${viewModelStore.keys}")
            }
        }
    }
}
