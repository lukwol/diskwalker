package com.diskusage.domain.repositories

import com.diskusage.domain.model.DiskEntry
import java.nio.file.Path

/**
 * Collects all information from given [Path], that are required to create [DiskEntry]
 * and builds its child items tree.
 */
interface DiskEntryRepository {
    fun diskEntry(path: Path): DiskEntry
}
