package com.diskusage.libraries.viewmodel.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.diskusage.libraries.screens.navigation.Arguments
import com.diskusage.libraries.screens.navigation.LocalScreensController
import com.diskusage.libraries.screens.navigation.ScreenRoute
import com.diskusage.libraries.screens.navigation.ScreensMapBuilder
import com.diskusage.libraries.viewmodel.ViewModel
import kotlinx.coroutines.cancel

/**
 * Declare screen [content] for [route] and provide it with [ViewModel].
 *
 * @param VM generic parameter of [ViewModel]
 * @param route [ScreenRoute] used to navigate to the [screen]
 * @param viewModelFactory lambda that takes screen [Arguments] and creates [ViewModel]
 * @param content [Composable] content of the screen
 *
 * @see ScreensMapBuilder.screen
 */
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
