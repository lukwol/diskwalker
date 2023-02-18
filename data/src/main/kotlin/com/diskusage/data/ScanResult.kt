package com.diskusage.data

import com.diskusage.domain.model.scan.ScanItem
import java.nio.file.Path

internal class ScanResult(
    val children: Map<Path, Set<Path>>,
    val scanItems: Map<Path, ScanItem>
)
