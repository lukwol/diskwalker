package com.diskusage.domain.usecases.path

import java.nio.file.Path

class GetName(private val getPathInfo: GetPathInfo) {

    operator fun invoke(path: Path) = getPathInfo(path).name
}
