package com.diskusage.domain.usecases.scan

import com.diskusage.domain.model.path.PathInfo
import com.diskusage.domain.services.ScanRepository
import com.diskusage.domain.usecases.path.GetPathInfo
import java.nio.file.Path

class GetChildren(
    private val repository: ScanRepository,
    private val getPathInfo: GetPathInfo
) {

    operator fun invoke(path: Path) = when (getPathInfo(path)) {
        is PathInfo.Directory ->
            repository
                .runCatching { children(path) }
                .onFailure {
                    println("Failed to get children for path $path")
                }
                .getOrNull()
                .orEmpty()
        is PathInfo.File -> emptySet()
    }
}
