package com.diskwalker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.presentation.screens.chart.ChartScreen
import com.diskwalker.presentation.screens.chart.ChartViewModel
import com.diskwalker.presentation.screens.dashboard.DashboardScreen
import com.diskwalker.presentation.screens.dashboard.DashboardViewModel
import com.diskwalker.presentation.screens.scan.ScanScreen
import com.diskwalker.presentation.screens.scan.ScanViewModel
import io.github.lukwol.cmnav.screens.vm.ScreensNavigation
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import java.nio.file.Path

@Composable
fun AppNavigation() {
    ScreensNavigation(
        startRoute = AppRoutes.DashboardScreen,
    ) {
        screen(
            route = AppRoutes.DashboardScreen,
            viewModelFactory = { koinInject<DashboardViewModel>() },
        ) { viewModel ->
            DashboardScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }

        screen(
            route = AppRoutes.ScanScreen,
            viewModelWithArgs = { diskInfo: DiskInfo? ->
                koinInject<ScanViewModel>(parameters = { parametersOf(diskInfo) })
            },
        ) { viewModel ->
            ScanScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }

        screen(
            route = AppRoutes.ChartScreen,
            viewModelWithArgs = { path: Path? ->
                koinInject<ChartViewModel>(parameters = { parametersOf(path) })
            },
        ) { viewModel ->
            ChartScreen(
                state = viewModel.stateFlow.collectAsState().value,
                commands = viewModel::onCommand,
            )
        }
    }
}
