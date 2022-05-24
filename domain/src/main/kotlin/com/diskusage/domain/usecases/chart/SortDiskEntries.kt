package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry

class SortDiskEntries {
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareBy(DiskEntry::size).thenByDescending(DiskEntry::name))
}
