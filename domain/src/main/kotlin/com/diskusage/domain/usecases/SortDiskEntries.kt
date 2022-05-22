package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class SortDiskEntries {
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareByDescending(DiskEntry::size).thenBy(DiskEntry::name))
}
