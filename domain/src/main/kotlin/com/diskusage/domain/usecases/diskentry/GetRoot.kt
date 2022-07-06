package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry
import org.koin.core.annotation.Single

/**
 * Traverse through all parents to find topmost [DiskEntry]
 */
@Single
class GetRoot {
    operator fun invoke(diskEntry: DiskEntry): DiskEntry {
        return diskEntry.parent?.let(::invoke) ?: diskEntry
    }
}
