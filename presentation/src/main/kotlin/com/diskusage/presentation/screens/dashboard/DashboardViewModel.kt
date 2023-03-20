package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.usecases.disk.GetSystemInfo
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val getSystemInfo: GetSystemInfo,
) : ViewModel(),
    AsyncState<DashboardViewState> by AsyncState.Delegate(
        DashboardViewState(
            systemInfo = getSystemInfo(),
        ),
    ) {

    fun onCommand(command: DashboardCommand) {}
}
