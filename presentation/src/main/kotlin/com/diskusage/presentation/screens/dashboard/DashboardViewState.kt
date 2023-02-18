package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.model.disk.DiskInfo
import io.github.anvell.async.Async
import io.github.anvell.async.Uninitialized

data class DashboardViewState(
    val diskInfo: DiskInfo? = null,
    val scanState: Async<Unit> = Uninitialized
)
