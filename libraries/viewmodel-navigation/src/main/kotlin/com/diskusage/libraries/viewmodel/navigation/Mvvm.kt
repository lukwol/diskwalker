package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.LocalNavController
import com.diskusage.libraries.navigation.NavArguments
import com.diskusage.libraries.navigation.NavMapBuilder
import com.diskusage.libraries.navigation.NavRoute
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> NavMapBuilder.mvvm(
    route: NavRoute,
    viewModelFactory: (args: NavArguments?) -> VM,
    content: @Composable (viewModel: VM) -> Unit
) {
    composable(route) { arguments ->
        val navController = LocalNavController.current
        val viewModel = remember(route) {
            ViewModelStore.viewModels.getOrPut(route) { viewModelFactory(arguments) } as VM
        }

        content(viewModel)

        DisposableEffect(route) {
            onDispose {
                viewModel.viewModelScope.cancel()
                if (route !in navController.routes) {
                    ViewModelStore.viewModels.remove(route)
                }
            }
        }
    }
}
