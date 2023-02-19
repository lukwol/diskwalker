package com.diskusage.domain.usecases.path

import java.nio.file.Path

class GetSizeOnDisk(private val getPathInfo: GetPathInfo) {

    operator fun invoke(path: Path) = getPathInfo(path).sizeOnDisk
}
