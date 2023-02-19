package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskInfo
import com.diskusage.domain.usecases.scan.ScanDisk
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.Job

class DashboardViewModel(
    private val scanDisk: ScanDisk,
    private val getDiskInfo: GetDiskInfo
) : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    private var scanDiskJob: Job? = null

    init {
        setState {
            copy(diskInfo = getDiskInfo(Constants.Disk.RootDiskPath))
        }
    }

    fun onCommand(command: DashboardCommand) {
        when (command) {
            is SelectScannedPath -> {
                scanDiskJob = if (scanDiskJob != null) {
                    scanDiskJob?.cancel()
                    null
                } else {
                    scanDisk(command.path)
                        .collectAsyncAsState(viewModelScope) {
                            copy(scanState = it)
                        }
                }
            }
        }
    }
}
