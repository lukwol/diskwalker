package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import io.github.anvell.async.state.AsyncState
import io.github.anvell.async.state.asyncWithScope
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel(
    private val getDiskEntry: GetDiskEntry
) : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    fun onCommand(command: DashboardCommand) = with(command) {
        when (this) {
            is SelectScannedPath ->
                viewModelScope
                    .asyncWithScope { getDiskEntry(path) }
                    .catchAsState { copy(selectedDiskEntry = it) }
        }
    }
}
