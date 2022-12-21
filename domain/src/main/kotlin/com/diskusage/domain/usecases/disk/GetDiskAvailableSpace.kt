package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.DisksService
import com.diskusage.libraries.formatters.FileSizeFormatter

class GetDiskAvailableSpace(
    private val disksService: DisksService
) {
    operator fun invoke(path: String) = disksService
        .availableSpace(path)
        .let(FileSizeFormatter::toSiFormat)
}
