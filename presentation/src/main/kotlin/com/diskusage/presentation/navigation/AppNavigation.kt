package com.diskusage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.diskusage.domain.model.DiskEntry
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.screens.chart.ChartScreen
import com.diskusage.presentation.screens.dashboard.DashboardScreen
import io.github.lukwol.viewmodel.screens.navigation.ScreensNavigation

@Composable
fun AppNavigation() {
    ScreensNavigation(
        startRoute = AppRoutes.DashboardScreen
    ) {
        screen(
            route = AppRoutes.DashboardScreen,
            viewModelFactory = { ViewModelProvider.getDashboardViewModel() }
        ) { viewModel ->
            DashboardScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand
            )
        }

        screen(
            route = AppRoutes.ChartScreen,
            viewModelFactory = { ViewModelProvider.getChartViewModel(it as DiskEntry) }
        ) { viewModel ->
            ChartScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand
            )
        }
    }
}
