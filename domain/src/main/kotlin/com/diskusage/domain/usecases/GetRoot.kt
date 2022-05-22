package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetRoot {
    operator fun invoke(diskEntry: DiskEntry): DiskEntry {
        return diskEntry.parent?.let(::invoke) ?: diskEntry
    }
}
