package com.diskwalker.domain.model.system

import com.diskwalker.domain.model.disk.DiskInfo

data class SystemInfo(
    val disks: List<DiskInfo>,
)
