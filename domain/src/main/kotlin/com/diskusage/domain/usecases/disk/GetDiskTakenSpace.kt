package com.diskusage.domain.usecases.disk

import com.diskusage.domain.services.DisksService

class GetDiskTakenSpace(
    private val disksService: DisksService
) {
    operator fun invoke(path: String) = disksService
        .run { totalSpace(path) - availableSpace(path) }
}
