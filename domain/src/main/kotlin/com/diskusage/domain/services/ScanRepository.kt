package com.diskusage.domain.services

import com.diskusage.domain.model.DiskInfo
import com.diskusage.domain.model.scan.ScanItem
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface ScanRepository {
    suspend fun scanDisk(disk: Path): Flow<Float>

    fun diskInfo(): DiskInfo

    fun scanItem(path: Path): ScanItem

    fun children(path: Path): Set<Path>
}
