package com.diskusage.domain.usecases.path

import com.diskusage.domain.repositories.ScanRepository
import java.nio.file.Path

class GetPathInfo(private val repository: ScanRepository) {

    operator fun invoke(path: Path) = repository
        .runCatching { pathInfo(path) }
        .onFailure {
            println("Failed to get scan item for path $path")
        }
        .getOrThrow()
}
