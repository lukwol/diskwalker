package com.diskusage.domain.usecases.disk

import com.diskusage.domain.common.Constants
import com.diskusage.domain.services.DisksService
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class GetDiskName(
    private val disksService: DisksService
) {
    operator fun invoke(path: Path) = disksService
        .name(path.absolutePathString())
        ?: Constants.Disk.DefaultDiskName
}
