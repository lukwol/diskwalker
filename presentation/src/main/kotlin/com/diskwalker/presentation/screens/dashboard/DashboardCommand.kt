package com.diskwalker.presentation.screens.dashboard

sealed interface DashboardCommand {
    object ReloadDiskInfo : DashboardCommand
}
