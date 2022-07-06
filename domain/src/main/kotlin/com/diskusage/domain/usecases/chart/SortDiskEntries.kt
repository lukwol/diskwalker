package com.diskusage.domain.usecases.chart

import com.diskusage.domain.entities.DiskEntry
import org.koin.core.annotation.Single

/**
 * Sorts given list of `diskEntries`, first by [size][DiskEntry.sizeOnDisk] - from the biggest to the smallest,
 * if the size matches, sorts by [name][DiskEntry.name].
 */
@Single
class SortDiskEntries {
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareByDescending(DiskEntry::sizeOnDisk).thenBy(DiskEntry::name))
}
