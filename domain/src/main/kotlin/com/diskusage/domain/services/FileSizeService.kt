package com.diskusage.domain.services

interface FileSizeService {
    fun sizeOnDisk(path: String): Long
    fun sizeOnDiskFast(path: String): Long
}
