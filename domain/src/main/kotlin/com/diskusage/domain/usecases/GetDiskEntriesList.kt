package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetDiskEntriesList {
    operator fun invoke(diskEntry: DiskEntry): List<DiskEntry> {
        return listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children.flatMap(::invoke)
            else -> listOf()
        }
    }
}
