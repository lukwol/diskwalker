package com.diskusage.libraries.support.filesize

import com.diskusage.domain.services.FileSizeService

class FileSizeServiceImpl : FileSizeService {
    external override fun sizeOnDisk(path: String): Long
    external override fun sizeOnDiskFast(path: String): Long
}
