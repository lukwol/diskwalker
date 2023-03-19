package com.diskusage.data.mappers

import com.diskusage.domain.model.disk.DiskInfo
import com.diskwalker.support.Disk.DiskInfoDto

object DiskInfoMapper {
    fun map(input: DiskInfoDto) = input.run {
        DiskInfo(
            name = name,
            mountPoint = mountPoint,
            totalSpace = totalSpace,
            availableSpace = availableSpace,
            takenSpace = run { totalSpace - availableSpace },
            isRemovable = isRemovable,
            diskType = diskType.let(DiskTypeMapper::map),
            fileSystem = fileSystem,
        )
    }
}
