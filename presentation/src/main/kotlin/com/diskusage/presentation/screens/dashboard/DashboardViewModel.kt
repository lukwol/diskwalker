package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import io.github.anvell.async.Fail
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
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
        mutableViewState.value = copy(selectedDiskEntry = Loading)

        viewModelScope.launch {
            mutableViewState.value = copy(
                selectedDiskEntry = runCatching {
                    getDiskEntry(path)
                }.fold(
                    onSuccess = { Success(it) },
                    onFailure = { Fail(it) }
                )
            )
        }
    }
}
