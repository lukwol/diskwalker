package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.SystemInfoService

class GetSystemInfo(
    private val systemInfoService: SystemInfoService,
) {
    operator fun invoke() = systemInfoService.systemInfo()
}
