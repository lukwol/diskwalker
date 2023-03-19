package com.diskusage.presentation.screens.dashboard

import io.github.anvell.async.state.AsyncState
import io.github.lukwol.viewmodel.ViewModel

class DashboardViewModel() : ViewModel(), AsyncState<DashboardViewState> by AsyncState.Delegate(DashboardViewState()) {

    fun onCommand(command: DashboardCommand) {}
}
