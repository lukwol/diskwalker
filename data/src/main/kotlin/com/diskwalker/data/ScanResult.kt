package com.diskwalker.data

import com.diskwalker.domain.model.path.PathInfo
import java.nio.file.Path

internal class ScanResult(
    val pathChildren: Map<Path, Set<Path>>,
    val pathInfo: Map<Path, PathInfo>,
)
