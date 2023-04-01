package com.diskusage.presentation.screens.scan

import com.diskusage.domain.model.disk.DiskInfo
import com.diskusage.domain.usecases.scan.ScanDisk
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel

class ScanViewModel(
    diskInfo: DiskInfo,
    scanDisk: ScanDisk,
) : ViewModel(),
    AsyncState<ScanViewState> by AsyncState.Delegate(
        ScanViewState(diskInfo = diskInfo),
    ) {
    init {
        scanDisk.invoke(diskInfo.mountPoint)
            .collectAsyncAsState(viewModelScope) {
                copy(scanState = it)
            }
    }

    fun onCommand(command: ScanCommand) {}
}
