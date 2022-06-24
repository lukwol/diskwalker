package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry

class SortDiskEntries {

    /**
     * Sorts [diskEntries] first by [size][DiskEntry.sizeOnDisk] from the biggest to the smallest,
     * if the size matches, sorts by [name][DiskEntry.name].
     *
     * @param diskEntries unsorted [List] of [disk entries][DiskEntry]
     *
     * @return [List] of sorted [disk entries][DiskEntry]
     */
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareByDescending(DiskEntry::sizeOnDisk).thenBy(DiskEntry::name))
}
