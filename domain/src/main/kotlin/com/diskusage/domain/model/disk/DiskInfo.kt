package com.diskusage.domain.model.disk

data class DiskInfo(
    val name: String,
    val mountPoint: String,
    val totalSpace: Long,
    val availableSpace: Long,
    val takenSpace: Long,
    val isRemovable: Boolean,
    val diskType: DiskType,
    val fileSystem: String,
)
