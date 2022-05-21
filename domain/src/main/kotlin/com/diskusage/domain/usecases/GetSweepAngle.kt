package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetSweepAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry,
    ) = when (fromDiskEntry.relationship(diskEntry)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Ancestor -> 360f
        DiskEntry.Relationship.Descendant -> (diskEntry.size.toFloat() / fromDiskEntry.size.toFloat())
            .takeIf(Float::isFinite)
            ?.times(360f)
            ?: 0f
        else -> 0f
    }
}
