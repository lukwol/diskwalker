package com.diskusage.domain.usecases.disk

import java.nio.file.Path

class GetDiskTotalSpace {
    operator fun invoke(path: Path) = path.toFile().totalSpace
}
