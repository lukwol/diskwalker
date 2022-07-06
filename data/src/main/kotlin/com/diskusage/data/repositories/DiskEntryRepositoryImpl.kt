package com.diskusage.data.repositories

import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.services.FileSizeService
import org.koin.core.annotation.Single
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

@Single
class DiskEntryRepositoryImpl(private val fileSizeService: FileSizeService) : DiskEntryRepository {

    private val cachedSizes = mutableMapOf<Path, Long>()

    override fun diskEntry(path: Path): DiskEntry {
        return diskEntry(path, null)
    }

    private fun diskEntry(
        path: Path,
        parent: DiskEntry.Directory?,
    ): DiskEntry = when {
        path.isRegularFile() -> DiskEntry.File(
            path = path,
            name = path.name,
            parent = parent
        )
        else -> DiskEntry.Directory(
            path = path,
            name = path.name,
            parent = parent,
        ).apply {
            children = path
                .listDirectoryEntries()
                .map { diskEntry(it, this) }
        }
    }.apply {
        sizeOnDisk = sizeOnDisk()
    }

    private fun DiskEntry.sizeOnDisk(): Long = cachedSizes[path]
        ?: when (this) {
            is DiskEntry.Directory -> children.sumOf { it.sizeOnDisk() }
            is DiskEntry.File -> fileSizeService.sizeOnDisk(path.absolutePathString())
        }.also { size ->
            cachedSizes[path] = size
        }
}
