package com.diskusage.domain.services

import com.diskusage.domain.model.scan.ScanItem
import io.github.anvell.async.Async
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface ScanRepository {
    fun scanDisk(disk: Path): Flow<Async<Unit>>

    fun scanItem(path: Path): ScanItem

    fun children(path: Path): Set<Path>
}
