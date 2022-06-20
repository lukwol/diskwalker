package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry

class SortDiskEntries {
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareByDescending(DiskEntry::sizeOnDisk).thenBy(DiskEntry::name))
}
