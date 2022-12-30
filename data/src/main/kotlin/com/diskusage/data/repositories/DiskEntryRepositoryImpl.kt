package com.diskusage.data.repositories

import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.services.FileSizeService
import com.diskusage.domain.usecases.disk.GetDiskTakenSpace
import io.github.anvell.async.Async
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class DiskEntryRepositoryImpl(
    private val fileSizeService: FileSizeService,
    private val getDiskTakenSpace: GetDiskTakenSpace
) : DiskEntryRepository {

    private var calculatedSpace = 0L

    private var diskTakenSpace = 0L

    private val cachedSizes = mutableMapOf<Path, Long>()

    override fun diskEntry(
        path: Path,
        name: String
    ) = flow {
        diskTakenSpace = getDiskTakenSpace(Constants.Disk.RootDiskPath)
        val diskEntry = diskEntry(path, null).also { it.name = name }
        emit(Loading(1f))
        delay(300)
        emit(Success(diskEntry))
    }

    private suspend fun FlowCollector<Async<DiskEntry>>.diskEntry(
        path: Path,
        parent: DiskEntry?,
        name: String = path.name
    ): DiskEntry = DiskEntry(
        path = path,
        name = name,
        parent = parent,
        type = if (path.isDirectory(LinkOption.NOFOLLOW_LINKS)) {
            DiskEntry.Type.Directory
        } else {
            DiskEntry.Type.File
        }
    ).apply {
        if (type == DiskEntry.Type.Directory) {
            children = runCatching {
                path
                    .listDirectoryEntries()
                    .filterNot { it.absolutePathString() in Constants.Disk.UncheckedPaths }
                    .map { diskEntry(it, this) }
            }.getOrNull() ?: emptyList()
        }
    }.apply {
        sizeOnDisk = sizeOnDisk().also {
            val progress = (calculatedSpace.toFloat() / diskTakenSpace.toFloat()).coerceAtMost(0.99f)
            emit(Loading(progress))
        }
    }

    private fun DiskEntry.sizeOnDisk(): Long = when (this.type) {
        DiskEntry.Type.Directory -> cachedSizes[path]
            ?: children.sumOf(DiskEntry::sizeOnDisk)
                .also { cachedSizes[path] = it }

        DiskEntry.Type.File -> runCatching {
            fileSizeService
                .sizeOnDisk(path.absolutePathString())
                .also { calculatedSpace += it }
        }.getOrNull() ?: 0
    }
}
