package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.common.Constants
import com.diskusage.domain.usecases.disk.GetDiskName
import com.diskusage.domain.usecases.disk.GetDiskTakenSpace
import com.diskusage.domain.usecases.disk.GetDiskTotalSpace
import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import com.diskusage.libraries.formatters.FileSizeFormatter
import io.github.anvell.async.Loading
import io.github.anvell.async.state.AsyncState
import io.github.anvell.async.state.asyncWithScope
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val getDiskEntry: GetDiskEntry,
    private val getDiskName: GetDiskName,
    private val getDiskTotalSpace: GetDiskTotalSpace,
    private val getDiskTakenSpace: GetDiskTakenSpace
) : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    init {
        viewModelScope
            .asyncWithScope { getDiskName(Constants.Disk.RootDiskPath) }
            .catchAsState { copy(diskName = it) }

        viewModelScope
            .asyncWithScope {
                getDiskTotalSpace(Constants.Disk.RootDiskPath)
                    .let(FileSizeFormatter::toSiFormat)
            }
            .catchAsState { copy(totalDiskSpace = it) }

        viewModelScope
            .asyncWithScope {
                getDiskTakenSpace(Constants.Disk.RootDiskPath)
                    .let(FileSizeFormatter::toSiFormat)
            }
            .catchAsState { copy(takenDiskSpace = it) }
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
