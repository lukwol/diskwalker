package com.diskusage.data.services

import SupportJni
import com.diskusage.domain.services.FileSizeService

class FileSizeServiceImpl(
    private val supportJni: SupportJni
) : FileSizeService {
    override fun sizeOnDisk(path: String) = supportJni.sizeOnDisk(path)
}
