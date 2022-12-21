package com.diskusage.domain.usecases.disk

import com.diskusage.domain.common.Constants
import com.diskusage.domain.services.DisksService

class GetDiskName(
    private val disksService: DisksService
) {
    operator fun invoke(path: String) = disksService.name(path)
        ?: Constants.Disk.DefaultDiskName
}
