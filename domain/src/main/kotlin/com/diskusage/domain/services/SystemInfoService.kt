package com.diskusage.domain.services

import com.diskusage.domain.model.system.SystemInfo

interface SystemInfoService {
    fun systemInfo(): SystemInfo
}
