package com.diskusage.data.repositories

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.services.FileSizeService
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class DiskEntryRepositoryImpl(private val fileSizeService: FileSizeService) : DiskEntryRepository {

    private val cachedSizes = mutableMapOf<Path, Long>()

    override fun diskEntry(path: Path): DiskEntry {
        return diskEntry(path, null)
    }

    private fun diskEntry(
        path: Path,
        parent: DiskEntry?
    ): DiskEntry = DiskEntry(
        path = path,
        name = path.name,
        parent = parent,
        type = if (path.isRegularFile()) DiskEntry.Type.File else DiskEntry.Type.Directory
    )
        .apply {
            if (type == DiskEntry.Type.Directory) {
                children = path
                    .listDirectoryEntries()
                    .map { diskEntry(it, this) }
            }
        }
        .apply {
            sizeOnDisk = sizeOnDisk()
        }

    private fun DiskEntry.sizeOnDisk(): Long = cachedSizes[path]
        ?: when (this.type) {
            DiskEntry.Type.Directory -> children.sumOf { it.sizeOnDisk() }
            DiskEntry.Type.File -> fileSizeService.sizeOnDisk(path.absolutePathString())
        }.also { size ->
            cachedSizes[path] = size
        }
}
