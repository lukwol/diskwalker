package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.services.DisksService
import io.github.anvell.async.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.nio.file.Path

/**
 * Obtain [DiskEntry] from [DiskEntryRepository]
 */
class GetDiskEntry(
    private val diskEntryRepository: DiskEntryRepository,
    private val disksService: DisksService
) {
    operator fun invoke(path: Path): Flow<Async<DiskEntry>> = diskEntryRepository
        .diskEntryForDisk(path)
        .flowOn(Dispatchers.Default)
}
