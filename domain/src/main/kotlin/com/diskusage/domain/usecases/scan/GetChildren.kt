package com.diskusage.domain.usecases.scan

import com.diskusage.domain.repositories.ScanRepository
import com.diskusage.domain.usecases.path.IsFile
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
