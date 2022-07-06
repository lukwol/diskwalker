package com.diskusage.libraries.support.filesize

import com.diskusage.domain.services.FileSizeService
import org.koin.core.annotation.Single

@Single
class FileSizeServiceFFI : FileSizeService {
    external override fun sizeOnDisk(path: String): Long
    external override fun sizeOnDiskFast(path: String): Long
}
