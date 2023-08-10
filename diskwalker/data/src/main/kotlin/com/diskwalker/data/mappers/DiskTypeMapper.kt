package com.diskwalker.data.mappers

import com.diskwalker.domain.model.disk.DiskType
import com.diskwalker.support.Disk.DiskTypeDto

object DiskTypeMapper {
    fun map(input: DiskTypeDto) = when (input) {
        DiskTypeDto.SSD -> DiskType.SSD
        DiskTypeDto.HDD -> DiskType.HDD
        DiskTypeDto.Unknown, DiskTypeDto.UNRECOGNIZED -> DiskType.Unknown
    }
}
