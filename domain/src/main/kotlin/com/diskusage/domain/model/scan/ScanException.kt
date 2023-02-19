package com.diskusage.domain.model.scan

import java.nio.file.Path

sealed class ScanException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    object ScanNotPerformed : ScanException("No directory was scanned")

    class MissingPathInfo(path: Path) : ScanException("No path info was found for path: $path")

    class MissingChildren(path: Path) : ScanException("No children were found for path: $path")
}
