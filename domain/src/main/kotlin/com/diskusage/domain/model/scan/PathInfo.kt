package com.diskusage.domain.model.scan

sealed interface PathInfo {
    val sizeOnDisk: Long

    @JvmInline
    value class File(override val sizeOnDisk: Long) : PathInfo

    @JvmInline
    value class Directory(override val sizeOnDisk: Long) : PathInfo
}
