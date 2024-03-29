package com.diskwalker.domain.repositories

import com.diskwalker.domain.model.path.PathInfo
import io.github.anvell.async.Async
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path

interface ScanRepository {
    fun scanDisk(disk: Path): Flow<Async<Unit>>

    fun pathInfo(path: Path): PathInfo

    fun children(path: Path): Set<Path>
}
