package com.diskusage.data.services

import com.diskusage.data.mappers.SystemInfoMapper
import com.diskusage.domain.services.SystemInfoService
import com.diskusage.libraries.support.SupportJni
import com.diskwalker.support.System.SystemInfoDto

class SystemInfoServiceImpl(
    private val supportJni: SupportJni
) : SystemInfoService {
    override fun systemInfo() = supportJni
        .serializedSystemInfo()
        .let(SystemInfoDto::parseFrom)
        .let(SystemInfoMapper::map)
}
