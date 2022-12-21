package com.diskusage.presentation.screens.dashboard

import java.nio.file.Path

sealed interface DashboardCommand
data class SelectScannedPath(val path: Path) : DashboardCommand
