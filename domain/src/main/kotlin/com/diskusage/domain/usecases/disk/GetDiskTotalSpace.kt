package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.DisksService
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class GetDiskTotalSpace(
    private val disksService: DisksService
) {
    operator fun invoke(path: Path) = disksService.totalSpace(path.absolutePathString())
}
