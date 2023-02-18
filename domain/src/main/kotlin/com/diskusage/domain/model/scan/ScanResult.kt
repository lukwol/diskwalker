package com.diskusage.domain.model.scan

import com.diskusage.domain.model.DiskInfo
import java.nio.file.Path

class ScanResult(
    val diskInfo: DiskInfo,
    val children: Map<Path, Set<Path>>,
    val scanItems: Map<Path, ScanItem>
)
