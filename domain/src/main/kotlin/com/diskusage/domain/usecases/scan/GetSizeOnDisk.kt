package com.diskusage.domain.usecases.scan

import java.nio.file.Path

class GetSizeOnDisk(private val getScanItem: GetScanItem) {

    operator fun invoke(path: Path) = getScanItem(path).sizeOnDisk
}
