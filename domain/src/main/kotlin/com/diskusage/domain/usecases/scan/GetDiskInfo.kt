package com.diskusage.domain.usecases.scan

import com.diskusage.domain.services.ScanRepository

class GetDiskInfo(private val repository: ScanRepository) {

    operator fun invoke() = repository
        .runCatching(ScanRepository::diskInfo)
}
