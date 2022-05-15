package com.diskusage.data.datasources

import com.diskusage.domain.datasources.DiskEntryDataSource
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.support.FileSize
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

class DiskEntryDataSourceImpl(private val fileSize: FileSize) : DiskEntryDataSource {

    override fun diskEntry(path: Path): DiskEntry {
        return diskEntry(path, null)
    }

    private fun diskEntry(
        path: Path,
        parent: DiskEntry?,
    ): DiskEntry {
        val diskEntry = when {
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
        }
        diskEntry.size = sizeOnDisk(diskEntry)
        return diskEntry
    }

    private fun sizeOnDisk(diskEntry: DiskEntry): Long {
        return if (diskEntry.hasSizeCalculated) {
            diskEntry.size
        } else {
            try {
                when (diskEntry) {
                    is DiskEntry.Directory -> {
                        diskEntry.children.sumOf { sizeOnDisk(it) }
                    }
                    is DiskEntry.File -> {
                        fileSize.sizeOnDisk(diskEntry.path.absolutePathString())
                    }
                }
            } finally {
                diskEntry.hasSizeCalculated = true
            }
        }
    }
}
