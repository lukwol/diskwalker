package com.diskusage.presentation.screens.scan

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskInfo
import com.diskusage.domain.usecases.scan.ScanDisk
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.Job

class ScanViewModel(
    private val scanDisk: ScanDisk,
    private val getDiskInfo: GetDiskInfo,
) : ViewModel(),
    AsyncState<ScanViewState> by AsyncState.Delegate(
        ScanViewState(
            diskInfo = getDiskInfo(Constants.Disk.RootDiskPath),
        ),
    ) {

    private var scanDiskJob: Job? = null

    fun onCommand(command: ScanCommand) {
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
