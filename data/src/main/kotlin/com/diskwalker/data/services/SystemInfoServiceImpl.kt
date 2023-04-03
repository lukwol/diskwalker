package com.diskwalker.data.services

import com.diskwalker.data.mappers.SystemInfoMapper
import com.diskwalker.domain.services.SystemInfoService
import com.diskwalker.libraries.support.SupportJni
import com.diskwalker.support.System.SystemInfoDto

class SystemInfoServiceImpl(
    private val supportJni: SupportJni,
) : SystemInfoService {
    override fun systemInfo() = supportJni
        .serializedSystemInfo()
        .let(SystemInfoDto::parseFrom)
        .let(SystemInfoMapper::map)
}
