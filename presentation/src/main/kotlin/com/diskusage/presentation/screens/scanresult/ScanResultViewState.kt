package com.diskusage.presentation.screens.scanresult

import com.diskusage.domain.model.DiskEntry

data class ScanResultViewState(
    val scannedDiskEntry: DiskEntry? = null
)
