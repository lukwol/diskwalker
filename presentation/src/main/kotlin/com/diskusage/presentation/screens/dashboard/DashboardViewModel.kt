package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskInfo
import com.diskusage.domain.usecases.scan.ScanDisk
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val scanDisk: ScanDisk,
    private val getDiskInfo: GetDiskInfo
) : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    init {
        setState {
            copy(diskInfo = getDiskInfo(Constants.Disk.RootDiskPath))
        }
    }

    fun onCommand(command: DashboardCommand) {
        when (command) {
            is SelectScannedPath -> scanDisk(command.path)
                .collectAsyncAsState(viewModelScope) {
                    copy(scanState = it)
                }
        }
    }
}
