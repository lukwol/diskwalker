package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.navigation.*
import com.diskusage.libraries.viewmodel.ViewModel
import com.diskusage.presentation.navigation.usage.screens.FirstScreen
import com.diskusage.presentation.navigation.usage.screens.FirstScreenViewModel
import com.diskusage.presentation.navigation.usage.screens.SecondScreen
import com.diskusage.presentation.navigation.usage.screens.SecondScreenViewModel
import kotlinx.coroutines.cancel

@Composable
fun AppNavigation() {
    NavHost(
        startRoute = AppRoutes.FirstScreen
    ) {
        composable(
            route = AppRoutes.FirstScreen,
            viewModelFactory = { FirstScreenViewModel() }
        ) {
            FirstScreen()
        }

        composable(
            route = AppRoutes.SecondScreen,
            viewModelFactory = { SecondScreenViewModel() }
        ) {
            SecondScreen()
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun <VM : ViewModel> NavMapBuilder.composable(
    route: NavRoute,
    viewModelFactory: (NavArguments?) -> VM,
    content: @Composable (VM) -> Unit
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

internal object ViewModelStore {
    val viewModels = mutableMapOf<NavRoute, ViewModel>()
}

/*
 *    ┌─────┐    depends on
 *    │ MVI ◄────────────────────┐
 *    └──┬──┘                    │
 *       │            ┌──────────┴─────┐
 *       │            │ MVI Navigation │
 *       │depends on  └──────────┬─────┘
 *       │                       │
 *       │                       │
 * ┌─────▼──────┐                │depends on
 * │ View Model ◄───────────┐    │
 * └────────────┘ depends on│    │
 *                          │    │
 *                    ┌─────┴────▼────┐
 *                    │ VM Navigation │
 *                    └─────┬─────────┘
 *                          │
 * ┌────────────┐ depends on│
 * │ Navigation ◄───────────┘
 * └────────────┘
 */
