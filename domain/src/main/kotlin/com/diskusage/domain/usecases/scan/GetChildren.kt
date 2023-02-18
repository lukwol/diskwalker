package com.diskusage.domain.usecases.scan

import com.diskusage.domain.services.ScanRepository
import java.nio.file.Path

class GetChildren(private val repository: ScanRepository) {

    operator fun invoke(path: Path) = repository
        .runCatching { children(path) }
}
