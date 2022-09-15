package com.diskusage.presentation.components.scanresult

import com.diskusage.domain.model.DiskEntry

data class ScanResultViewState(
    val scannedDiskEntry: DiskEntry? = null
)
