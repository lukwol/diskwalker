package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskInfo
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val getDiskInfo: GetDiskInfo,
) : ViewModel(),
    AsyncState<DashboardViewState> by AsyncState.Delegate(
        DashboardViewState(
            diskInfo = getDiskInfo(Constants.Disk.RootDiskPath),
        ),
    ) {

    fun onCommand(command: DashboardCommand) {}
}
