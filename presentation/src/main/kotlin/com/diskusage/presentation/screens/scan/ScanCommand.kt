package com.diskusage.presentation.screens.scan

import java.nio.file.Path

sealed interface ScanCommand
data class SelectScannedPath(val path: Path) : ScanCommand
