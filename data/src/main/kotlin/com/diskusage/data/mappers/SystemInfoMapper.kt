package com.diskusage.data.mappers

import com.diskusage.domain.model.system.SystemInfo
import com.diskwalker.support.System.SystemInfoDto

object SystemInfoMapper {
    fun map(input: SystemInfoDto) = SystemInfo(
        disks = input.disksList.map(DiskInfoMapper::map)
    )
}
