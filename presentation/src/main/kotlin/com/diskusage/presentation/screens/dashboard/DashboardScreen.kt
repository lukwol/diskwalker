package com.diskusage.presentation.screens.dashboard

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DashboardScreen(
    state: DashboardViewState,
    commands: (DashboardCommand) -> Unit,
) {
    Text(state.diskInfo.toString())
}
