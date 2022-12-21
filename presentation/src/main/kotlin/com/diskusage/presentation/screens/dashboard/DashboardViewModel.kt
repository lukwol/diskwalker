package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskAvailableSpace
import com.diskusage.domain.usecases.disk.GetDiskName
import com.diskusage.domain.usecases.disk.GetDiskTotalSpace
import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import io.github.anvell.async.Loading
import io.github.anvell.async.state.AsyncState
import io.github.anvell.async.state.asyncWithScope
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val getDiskEntry: GetDiskEntry,
    private val getDiskName: GetDiskName,
    private val getDiskTotalSpace: GetDiskTotalSpace,
    private val getDiskAvailableSpace: GetDiskAvailableSpace
) : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    init {
        viewModelScope
            .asyncWithScope { getDiskName(Constants.Disk.RootDiskPath) }
            .catchAsState { copy(diskName = it) }

        viewModelScope
            .asyncWithScope { getDiskTotalSpace(Constants.Disk.RootDiskPath) }
            .catchAsState { copy(diskTotalSpace = it) }

        viewModelScope
            .asyncWithScope { getDiskAvailableSpace(Constants.Disk.RootDiskPath) }
            .catchAsState { copy(diskAvailableSpace = it) }
    }

    fun onCommand(command: DashboardCommand) = with(command) {
        when (this) {
            is SelectScannedPath -> getDiskEntry(path)
                .collectAsyncAsState(
                    scope = viewModelScope,
                    initialState = Loading(0.0f)
                ) {
                    copy(selectedDiskEntry = it)
                }
        }
    }
}
