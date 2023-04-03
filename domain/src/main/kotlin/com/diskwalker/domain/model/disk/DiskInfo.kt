package com.diskwalker.domain.model.disk

import java.nio.file.Path

data class DiskInfo(
    val name: String,
    val mountPoint: Path,
    val totalSpace: Long,
    val availableSpace: Long,
    val takenSpace: Long,
    val isRemovable: Boolean,
    val diskType: DiskType,
    val fileSystem: String,
)
