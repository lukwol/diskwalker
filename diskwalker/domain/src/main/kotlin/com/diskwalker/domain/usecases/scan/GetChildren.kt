package com.diskwalker.domain.usecases.scan

import com.diskwalker.domain.repositories.ScanRepository
import com.diskwalker.domain.usecases.path.IsFile
import java.nio.file.Path

class GetChildren(
    private val repository: ScanRepository,
    private val isFile: IsFile,
) {

    operator fun invoke(path: Path): Set<Path> = runCatching {
        when (isFile(path)) {
            true -> emptySet()
            false -> repository.children(path)
        }
    }.getOrNull().orEmpty()
}
