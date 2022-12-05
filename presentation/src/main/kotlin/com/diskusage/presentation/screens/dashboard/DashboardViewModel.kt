package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.nio.file.Path

class DashboardViewModel(
    private val getDiskEntry: GetDiskEntry
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(DashboardViewState())

    val viewState = mutableViewState.asStateFlow()

    fun selectScannedPath(path: Path) = with(viewState.value) {
        viewModelScope.launch {
            mutableViewState.value = copy(
                selectedDiskEntry = getDiskEntry(path)
            )
        }
    }
}
