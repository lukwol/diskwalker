package com.diskusage.data.services

import com.diskusage.domain.services.FileSizeService
import com.diskusage.libraries.support.SupportJni

class FileSizeServiceImpl(
    private val supportJni: SupportJni,
) : FileSizeService {
    override fun sizeOnDisk(path: String) = supportJni.sizeOnDisk(path)
}
