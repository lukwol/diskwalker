package com.diskusage.domain.usecases.path

import java.nio.file.Path

class GetParent(private val getPathInfo: GetPathInfo) {

    operator fun invoke(path: Path) = getPathInfo(path).parent
}
