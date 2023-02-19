package com.diskusage.domain.usecases.disk

import java.nio.file.Path

class GetDiskTakenSpace {
    operator fun invoke(path: Path) = path
        .toFile()
        .run { totalSpace - usableSpace }
}
