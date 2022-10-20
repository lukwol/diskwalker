package com.diskusage.libraries.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.diskusage.libraries.navigation.NavArguments
import com.diskusage.libraries.navigation.NavMapBuilder
import com.diskusage.libraries.navigation.NavRoute
import com.diskusage.libraries.viewmodel.MviViewModel

fun <S, C> NavMapBuilder.mvi(
    route: NavRoute,
    viewModelFactory: (args: NavArguments?) -> MviViewModel<S, C>,
    content: @Composable (state: S, commands: (C) -> Unit) -> Unit
) {
    mvvm(
        route = route,
        viewModelFactory = viewModelFactory
    ) { viewModel ->
        val state = viewModel
            .stateAsFlow()
            .collectAsState()
            .value

        val commands = viewModel::dispatch

        content(state, commands)
    }
}
