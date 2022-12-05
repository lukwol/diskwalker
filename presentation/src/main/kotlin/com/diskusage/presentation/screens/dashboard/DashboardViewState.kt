package com.diskusage.presentation.screens.dashboard

import com.diskusage.domain.model.DiskEntry

data class DashboardViewState(
    val selectedDiskEntry: DiskEntry? = null
)
