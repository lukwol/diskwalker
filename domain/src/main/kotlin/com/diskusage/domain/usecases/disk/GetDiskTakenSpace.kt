package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.DisksService
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class GetDiskTakenSpace(
    private val disksService: DisksService
) {
    operator fun invoke(path: Path) = path.absolutePathString().let { pathString ->
        disksService.run { totalSpace(pathString) - availableSpace(pathString) }
    }
}
