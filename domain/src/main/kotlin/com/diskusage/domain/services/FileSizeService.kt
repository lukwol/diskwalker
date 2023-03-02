package com.diskusage.domain.services

/**
 * Provides information about size of the file
 */
interface FileSizeService {
    fun sizeOnDisk(path: String): Long
}
