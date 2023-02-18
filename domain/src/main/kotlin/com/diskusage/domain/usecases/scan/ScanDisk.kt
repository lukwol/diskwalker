package com.diskusage.domain.usecases.scan

import com.diskusage.domain.services.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import java.nio.file.Path

class ScanDisk(private val repository: ScanRepository) {

    suspend operator fun invoke(disk: Path) = repository
        .scanDisk(disk)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
}
