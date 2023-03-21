package com.diskusage.domain.usecases.path

import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Recursively traverses through all children of given `path`
 * and collect them to single [Set]
 */
class GetPathsSet(
    private val includePath: IncludePath,
    private val getChildren: GetChildren,
) {
    operator fun invoke(path: Path, disk: Path) = pathsSet(path, path, disk)

    private fun pathsSet(path: Path, fromPath: Path, disk: Path): Set<Path> =
        setOf(path) + getChildren(path)
            .filter { includePath(it, fromPath, disk) }
            .flatMap { pathsSet(it, fromPath, disk) }
}
