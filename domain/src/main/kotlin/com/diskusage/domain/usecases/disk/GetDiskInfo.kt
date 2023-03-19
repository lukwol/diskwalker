package com.diskusage.domain.usecases.disk

import java.nio.file.Path
import kotlin.io.path.pathString

class GetDiskInfo(
    private val getSystemInfo: GetSystemInfo,
) {
    operator fun invoke(path: Path) = getSystemInfo()
        .disks
        .first { it.mountPoint == path.pathString }
}
