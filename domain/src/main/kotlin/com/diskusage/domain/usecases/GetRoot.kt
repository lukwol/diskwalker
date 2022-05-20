package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetRoot {
    operator fun invoke(diskEntry: DiskEntry): DiskEntry = diskEntry.parent?.let(::invoke) ?: diskEntry
}
