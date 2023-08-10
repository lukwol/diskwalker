package com.diskwalker.domain.usecases.scan

import com.diskwalker.domain.repositories.ScanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import java.nio.file.Path

class ScanDisk(
    private val repository: ScanRepository,
) {
    operator fun invoke(disk: Path) = repository
        .scanDisk(disk)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
}
