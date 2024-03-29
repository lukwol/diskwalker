package com.diskwalker.data.mappers

import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.support.Disk.DiskInfoDto
import java.nio.file.Path

object DiskInfoMapper {
    fun map(input: DiskInfoDto) = input.run {
        DiskInfo(
            name = name,
            mountPoint = mountPoint.let(Path::of),
            totalSpace = totalSpace,
            availableSpace = availableSpace,
            takenSpace = run { totalSpace - availableSpace },
            isRemovable = isRemovable,
            diskType = diskType.let(DiskTypeMapper::map),
            fileSystem = fileSystem,
        )
    }
}
