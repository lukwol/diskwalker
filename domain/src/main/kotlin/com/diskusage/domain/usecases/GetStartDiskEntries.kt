package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetStartDiskEntries {
    operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry,
    ): List<DiskEntry> {
        return emptyList()
    }
}
