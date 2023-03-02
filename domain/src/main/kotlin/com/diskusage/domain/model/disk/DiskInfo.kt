package com.diskusage.domain.model.disk

data class DiskInfo(
    val name: String,
    val mountPoint: String,
    val totalSpace: Long,
    val availableSpace: Long,
    val isRemovable: Long,
    val diskType: DiskType,
    val fileSystem: String
)
