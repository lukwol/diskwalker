package com.diskusage.domain.services

import com.diskusage.domain.model.ScanResult
import io.github.anvell.async.Async
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface ScanService {
    suspend fun scanDisk(disk: Path): Flow<Async<ScanResult>>
}
