package com.diskusage.domain.usecases.disk

import com.diskusage.domain.model.disk.DiskInfo
import java.nio.file.Path

class GetDiskInfo(
    private val getDiskName: GetDiskName,
    private val getDiskTakenSpace: GetDiskTakenSpace,
    private val getDiskTotalSpace: GetDiskTotalSpace
) {
    operator fun invoke(disk: Path) = DiskInfo(
        path = disk,
        name = getDiskName(disk),
        takenSpace = getDiskTakenSpace(disk),
        totalSpace = getDiskTotalSpace(disk)
    )
}
