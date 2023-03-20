package com.diskusage.domain.usecases.scan

import com.diskusage.domain.repositories.ScanRepository
import com.diskusage.domain.usecases.path.IsFile
import java.nio.file.Path

class GetChildren(
    private val repository: ScanRepository,
    private val isFile: IsFile,
) {

    operator fun invoke(path: Path) = when (isFile(path)) {
        true -> emptySet()
        false ->
            repository
                .runCatching { children(path) }
                .onFailure {
                    println("Failed to get children for path $path")
                }
                .getOrNull()
                .orEmpty()
    }
}
