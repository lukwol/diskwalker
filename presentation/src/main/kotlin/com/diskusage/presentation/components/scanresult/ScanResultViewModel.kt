package com.diskusage.presentation.components.scanresult

import com.diskusage.domain.repositories.DiskEntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.file.Path

class ScanResultViewModel(
    private val diskEntryRepository: DiskEntryRepository
) {
    private val mutableViewState = MutableStateFlow(ScanResultViewState())
    val viewState = mutableViewState.asStateFlow()

    fun selectScannedPath(path: Path) = with(viewState.value) {
        mutableViewState.value = copy(
            scannedDiskEntry = diskEntryRepository.diskEntry(path)
        )
    }
}
