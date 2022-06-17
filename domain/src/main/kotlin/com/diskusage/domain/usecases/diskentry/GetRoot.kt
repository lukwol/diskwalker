package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.entities.DiskEntry

class GetRoot {

    /**
     * Traverse through all parents to find topmost [DiskEntry]
     *
     * @param diskEntry [DiskEntry] for which root will be looked for
     * @return Topmost root for given [diskEntry]
     */
    operator fun invoke(diskEntry: DiskEntry): DiskEntry {
        return diskEntry.parent?.let(::invoke) ?: diskEntry
    }
}
