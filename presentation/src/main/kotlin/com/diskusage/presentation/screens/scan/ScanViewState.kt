package com.diskusage.presentation.screens.scan

import com.diskusage.domain.model.DiskInfo
import io.github.anvell.async.Async
import io.github.anvell.async.Uninitialized

data class ScanViewState(
    val diskInfo: DiskInfo? = null,
    val scanState: Async<Unit> = Uninitialized,
)
