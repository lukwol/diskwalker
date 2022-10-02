package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.model.DiskEntry

/**
 * Traverse through all parents to find topmost [DiskEntry]
 */
class GetRoot {
    operator fun invoke(diskEntry: DiskEntry): DiskEntry {
        return diskEntry.parent?.let(::invoke) ?: diskEntry
    }
}
