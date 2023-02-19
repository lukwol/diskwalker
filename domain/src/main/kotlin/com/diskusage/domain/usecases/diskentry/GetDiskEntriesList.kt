package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Recursively traverses through all [children][DiskEntry.children] of given `diskEntry`
 * and collect them to single [List]
 */
class GetDiskEntriesList(
    private val includeDiskEntry: IncludeDiskEntry,
    private val getChildren: GetChildren
) {
    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)

    operator fun invoke(path: Path) = pathsList(path)

    private fun diskEntriesList(diskEntry: DiskEntry, fromDiskEntry: DiskEntry = diskEntry): List<DiskEntry> =
        listOf(diskEntry) + diskEntry.children
            .filter { includeDiskEntry(it, fromDiskEntry) }
            .flatMap { diskEntriesList(it, fromDiskEntry) }

    private fun pathsList(path: Path, fromPath: Path = path): List<Path> =
        listOf(path) + getChildren(path)
            .filter { includeDiskEntry(it, fromPath) }
            .flatMap { pathsList(it, fromPath) }
}
