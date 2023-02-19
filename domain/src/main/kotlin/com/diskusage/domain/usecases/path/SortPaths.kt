package com.diskusage.domain.usecases.path

import com.diskusage.domain.model.path.PathInfo
import java.nio.file.Path
import kotlin.io.path.name

/**
 * Sorts given list of `paths`, first by [size][PathInfo.sizeOnDisk] - from the biggest to the smallest,
 * if the size matches, sorts by [name][Path.name].
 */
class SortPaths(
    private val getSizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(paths: Collection<Path>) = paths
        .sortedWith(
            compareByDescending(getSizeOnDisk::invoke)
                .thenBy(Path::name)
        )
}
