package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.DisksService

class GetDiskTotalSpace(
    private val disksService: DisksService
) {
    operator fun invoke(path: String) = disksService.totalSpace(path)
}
