package com.diskusage.domain.datasources

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path

interface DiskEntryDataSource {
    fun diskEntry(path: Path): DiskEntry
}
