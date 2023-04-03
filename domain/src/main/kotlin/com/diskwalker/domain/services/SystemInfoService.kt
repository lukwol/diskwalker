package com.diskwalker.domain.services

import com.diskwalker.domain.model.system.SystemInfo

interface SystemInfoService {
    fun systemInfo(): SystemInfo
}
