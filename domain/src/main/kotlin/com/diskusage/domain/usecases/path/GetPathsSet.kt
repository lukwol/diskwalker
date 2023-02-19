package com.diskusage.domain.usecases.path

import com.diskusage.domain.usecases.scan.GetChildren
import java.nio.file.Path

/**
 * Recursively traverses through all children of given `path`
 * and collect them to single [Set]
 */
class GetPathsSet(
    private val includePath: IncludePath,
    private val getChildren: GetChildren
) {
    operator fun invoke(path: Path) = pathsSet(path)

    private fun pathsSet(path: Path, fromPath: Path = path): Set<Path> =
        setOf(path) + getChildren(path)
            .filter { includePath(it, fromPath) }
            .flatMap { pathsSet(it, fromPath) }
}
