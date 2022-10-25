package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.*
import com.diskusage.libraries.navigation.screens.LocalNavController
import com.diskusage.libraries.navigation.screens.NavArguments
import com.diskusage.libraries.navigation.screens.NavMapBuilder
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

@Suppress("UNCHECKED_CAST")
fun <VM : ViewModel> NavMapBuilder.composable(
    route: String,
    viewModelFactory: (args: NavArguments?) -> VM,
    content: @Composable (viewModel: VM) -> Unit
) {
    composable(route) { arguments ->
        val navController = LocalNavController.current

        val viewModelStore = remember { ViewModelStore() }
        val viewModel = remember(route) {
            viewModelStore.getOrPut(route) { viewModelFactory(arguments) } as VM
        }

        content(viewModel)

        DisposableEffect(route) {
            onDispose {
                viewModel.viewModelScope.cancel()
                if (route !in navController.routes) {
                    viewModelStore.remove(route)
                }
            }
        }
    }
}
