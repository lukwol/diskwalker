package com.diskusage.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.diskusage.presentation.di.ViewModelProvider
import com.diskusage.presentation.screens.chart.ChartScreen
import com.diskusage.presentation.screens.dashboard.DashboardScreen
import com.diskusage.presentation.screens.scan.ScanScreen
import io.github.lukwol.viewmodel.screens.navigation.ScreensNavigation
import java.nio.file.Path

@Composable
fun AppNavigation() {
    ScreensNavigation(
        startRoute = AppRoutes.DashboardScreen,
        animated = true,
    ) {
        screen(
            route = AppRoutes.DashboardScreen,
            viewModelFactory = { ViewModelProvider.getDashboardViewModel() },
        ) { viewModel ->
            DashboardScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }

        screen(
            route = AppRoutes.ScanScreen,
            viewModelFactory = { ViewModelProvider.getScanViewModel() },
        ) { viewModel ->
            ScanScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }

        screen(
            route = AppRoutes.ChartScreen,
            viewModelFactory = { ViewModelProvider.getChartViewModel(it as Path) },
        ) { viewModel ->
            ChartScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }
    }
}
