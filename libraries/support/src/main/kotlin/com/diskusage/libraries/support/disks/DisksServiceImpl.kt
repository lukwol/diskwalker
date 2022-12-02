package com.diskusage.libraries.support.disks

import com.diskusage.domain.services.DisksService

class DisksServiceImpl : DisksService {
    external override fun name(path: String): String?
    external override fun totalSpace(path: String): Long
    external override fun availableSpace(path: String): Long
}
