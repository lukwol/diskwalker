package com.diskwalker.data.mappers

import com.diskwalker.domain.model.system.SystemInfo
import com.diskwalker.support.System.SystemInfoDto

object SystemInfoMapper {
    fun map(input: SystemInfoDto) = SystemInfo(
        disks = input.disksList.map(DiskInfoMapper::map),
    )
}
