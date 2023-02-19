package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.usecases.scan.GetSizeOnDisk
import java.nio.file.Path
import kotlin.io.path.name

/**
 * Sorts given list of `diskEntries`, first by [size][DiskEntry.sizeOnDisk] - from the biggest to the smallest,
 * if the size matches, sorts by [name][DiskEntry.name].
 */
class SortDiskEntries(
    private val getSizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(diskEntries: List<DiskEntry>) = diskEntries
        .sortedWith(compareByDescending(DiskEntry::sizeOnDisk).thenBy(DiskEntry::name))

    operator fun invoke(paths: Collection<Path>) = paths
        .sortedWith(
            compareByDescending(getSizeOnDisk::invoke)
                .thenBy(Path::name)
        )
}
