package com.diskusage.presentation.screens.dashboard

sealed interface DashboardCommand {
    object ReloadDiskInfo : DashboardCommand
}
