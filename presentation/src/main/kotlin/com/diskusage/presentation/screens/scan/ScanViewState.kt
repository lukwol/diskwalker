package com.diskusage.presentation.screens.scan

import com.diskusage.domain.model.disk.DiskInfo
import io.github.anvell.async.Async
import io.github.anvell.async.Uninitialized

data class ScanViewState(
    val diskInfo: DiskInfo,
    val scanState: Async<Unit> = Uninitialized,
)
