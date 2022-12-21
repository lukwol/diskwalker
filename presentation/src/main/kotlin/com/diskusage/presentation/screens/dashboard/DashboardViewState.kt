package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.model.DiskEntry
import io.github.anvell.async.Async
import io.github.anvell.async.Uninitialized

data class DashboardViewState(
    val diskName: Async<String> = Uninitialized,
    val diskTotalSpace: Async<String> = Uninitialized,
    val diskAvailableSpace: Async<String> = Uninitialized,
    val selectedDiskEntry: Async<DiskEntry> = Uninitialized
)
