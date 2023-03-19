package com.diskusage.domain.model.system

import com.diskusage.domain.model.disk.DiskInfo

data class SystemInfo(
    val disks: List<DiskInfo>,
)
