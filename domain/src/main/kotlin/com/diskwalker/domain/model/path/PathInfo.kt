package com.diskwalker.domain.model.path

import java.nio.file.Path

data class PathInfo(
    val name: String,
    val parent: Path?,
    val sizeOnDisk: Long,
    val isFile: Boolean,
)
