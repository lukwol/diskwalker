package com.diskusage.presentation.navigation.usage.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.viewmodel.ViewModel
import com.diskusage.presentation.navigation.NavArguments
import com.diskusage.presentation.navigation.NavHost
import com.diskusage.presentation.navigation.NavMapBuilder
import com.diskusage.presentation.navigation.NavRoute
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

private fun <VM : ViewModel> NavMapBuilder.composable(
    route: NavRoute,
    viewModelFactory: (NavArguments?) -> VM,
    content: @Composable (VM) -> Unit
) {
    composable(route) { arguments ->
        val viewModel = remember(route) { viewModelFactory(arguments) }
        content(viewModel)
        DisposableEffect(route) {
            onDispose(viewModel.viewModelScope::cancel)
        }
    }
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
