package com.diskusage.support

interface FileSize {
    fun sizeOnDisk(path: String): Long
    fun sizeOnDiskFast(path: String): Long
}
