package com.diskwalker.domain.usecases.path

import com.diskwalker.domain.model.path.PathInfo
import java.nio.file.Path

/**
 * Sorts given list of `paths`, first by [size][PathInfo.sizeOnDisk] - from the biggest to the smallest,
 * if the size matches, sorts by [name][PathInfo.name].
 */
class SortPaths(
    private val getSizeOnDisk: GetSizeOnDisk,
    private val getName: GetName,
) {
    operator fun invoke(paths: Collection<Path>) = paths
        .sortedWith(
            compareByDescending(getSizeOnDisk::invoke)
                .thenBy(getName::invoke),
        )
}
