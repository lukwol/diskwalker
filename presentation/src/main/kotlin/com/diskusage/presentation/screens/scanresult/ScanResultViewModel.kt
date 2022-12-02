package com.diskusage.presentation.screens.scanresult

import com.diskusage.domain.usecases.diskentry.GetDiskEntry
import io.github.lukwol.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.nio.file.Path

class ScanResultViewModel(
    private val getDiskEntry: GetDiskEntry
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(ScanResultViewState())

    val viewState = mutableViewState.asStateFlow()

    fun selectScannedPath(path: Path) = with(viewState.value) {
        viewModelScope.launch {
            mutableViewState.value = copy(
                scannedDiskEntry = getDiskEntry(path)
            )
        }
    }
}
