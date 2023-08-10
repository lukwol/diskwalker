package com.diskwalker.presentation.screens.dashboard

import com.diskwalker.domain.usecases.disk.GetSystemInfo
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.cmnav.screens.vm.ViewModel

class DashboardViewModel(
    private val getSystemInfo: GetSystemInfo,
) : ViewModel(),
    AsyncState<DashboardViewState> by AsyncState.Delegate(
        DashboardViewState(
            systemInfo = getSystemInfo(),
        ),
    ) {

    fun onCommand(command: DashboardCommand) {
        when (command) {
            DashboardCommand.ReloadDiskInfo -> setState {
                copy(systemInfo = getSystemInfo())
            }
        }
    }
}
