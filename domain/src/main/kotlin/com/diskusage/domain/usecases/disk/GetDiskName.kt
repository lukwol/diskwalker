package com.diskusage.domain.usecases.disk

import com.diskusage.domain.common.Constants
import com.diskusage.domain.services.SystemInfoService
import java.nio.file.Path
import kotlin.io.path.pathString

class GetDiskName(
    private val systemInfoService: SystemInfoService,
) {
    operator fun invoke(path: Path) = systemInfoService
        .systemInfo()
        .disks
        .find { it.mountPoint == path.pathString }
        ?.name
        ?: Constants.Disk.DefaultDiskName
}
