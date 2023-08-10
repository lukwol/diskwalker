package com.diskwalker.domain.usecases.disk

import com.diskwalker.domain.services.SystemInfoService

class GetSystemInfo(
    private val systemInfoService: SystemInfoService,
) {
    operator fun invoke() = systemInfoService.systemInfo()
}
