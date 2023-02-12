package com.diskusage.domain.model

sealed interface ScanItem {
    val size: Long

    @JvmInline
    value class File(override val size: Long) : ScanItem

    @JvmInline
    value class Directory(override val size: Long) : ScanItem
}
