package com.diskusage.presentation.components.scanresult

import com.diskusage.domain.entities.DiskEntry

data class ScanResultViewState(
    val scannedDiskEntry: DiskEntry? = null
)
