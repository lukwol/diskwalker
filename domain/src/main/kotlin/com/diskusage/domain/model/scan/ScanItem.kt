package com.diskusage.domain.model.scan

sealed interface ScanItem {
    val sizeOnDisk: Long

    @JvmInline
    value class File(override val sizeOnDisk: Long) : ScanItem

    @JvmInline
    value class Directory(override val sizeOnDisk: Long) : ScanItem
}
