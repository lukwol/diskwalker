package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetStartDiskEntries {
    operator fun invoke(
        fromDiskEntry: DiskEntry,
        toDiskEntry: DiskEntry,
    ): List<DiskEntry> {
        // TODO: 15/05/2022 Implement
        return listOf()
    }
}
