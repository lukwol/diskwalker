package com.diskwalker.data.services

import com.diskwalker.domain.services.FileSizeService
import com.diskwalker.libraries.support.SupportJni

class FileSizeServiceImpl(
    private val supportJni: SupportJni,
) : FileSizeService {
    override fun sizeOnDisk(path: String) = supportJni.sizeOnDisk(path)
}
