package com.diskusage.domain.usecases.diskentry

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
    operator fun invoke(path: Path) = pathsList(path)

    private fun pathsList(path: Path, fromPath: Path = path): Set<Path> =
        setOf(path) + getChildren(path)
            .filter { includeDiskEntry(it, fromPath) }
            .flatMap { pathsList(it, fromPath) }
}
