package com.diskusage.domain.usecases.disk

import java.nio.file.Path

class GetDiskInfo(
    private val getSystemInfo: GetSystemInfo,
) {
    operator fun invoke(path: Path) = getSystemInfo()
        .disks
        .first { it.mountPoint == path }
}
