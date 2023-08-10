package com.diskwalker.presentation.screens.scan

import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.domain.usecases.scan.ScanDisk
import io.github.anvell.async.state.AsyncState
import io.github.lukwol.cmnav.screens.vm.ViewModel

class ScanViewModel(
    diskInfo: DiskInfo,
    scanDisk: ScanDisk,
) : ViewModel(),
    AsyncState<ScanViewState> by AsyncState.Delegate(
        ScanViewState(diskInfo = diskInfo),
    ) {
    init {
        scanDisk.invoke(diskInfo.mountPoint)
            .collectAsyncAsState(coroutineScope) {
                copy(scanState = it)
            }
    }

    fun onCommand(command: ScanCommand) {}
}
