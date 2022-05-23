package com.diskusage.domain.repositories

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path

interface DiskEntryRepository {
    fun diskEntry(path: Path): DiskEntry
}
