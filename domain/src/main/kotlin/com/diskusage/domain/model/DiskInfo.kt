package com.diskusage.domain.model

import java.nio.file.Path

data class DiskInfo(
    val path: Path,
    val name: String,
    val takenSpace: Long,
    val totalSpace: Long,
)
