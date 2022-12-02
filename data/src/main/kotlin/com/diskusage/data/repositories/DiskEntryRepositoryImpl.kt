package com.diskusage.data.repositories

import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.repositories.DiskEntryRepository
import com.diskusage.domain.services.DisksService
import com.diskusage.domain.services.FileSizeService
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class DiskEntryRepositoryImpl(
    private val fileSizeService: FileSizeService,
    private val disksService: DisksService
) : DiskEntryRepository {

    private val cachedSizes = mutableMapOf<Path, Long>()

    override fun diskEntry(path: Path): DiskEntry {
        val diskName = disksService.name(path.absolutePathString()) ?: Constants.Disk.DefaultDiskName
        return diskEntry(
            path = path,
            name = diskName,
            parent = null
        )
    }

    private fun diskEntry(
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
        sizeOnDisk = sizeOnDisk()
    }

    private fun DiskEntry.sizeOnDisk(): Long = cachedSizes[path]
        ?: when (this.type) {
            DiskEntry.Type.Directory -> children.sumOf { it.sizeOnDisk() }
            DiskEntry.Type.File -> runCatching {
                fileSizeService.sizeOnDisk(path.absolutePathString())
            }.getOrNull() ?: 0
        }.also { size ->
            cachedSizes[path] = size
        }
}
