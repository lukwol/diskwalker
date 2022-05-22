package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry

class GetSweepAngle {
    operator fun invoke(
        diskEntry: DiskEntry,
        fromDiskEntry: DiskEntry = diskEntry,
    ) = when (diskEntry.relationship(fromDiskEntry)) {
        DiskEntry.Relationship.Identity, DiskEntry.Relationship.Descendant -> 360f
        DiskEntry.Relationship.Ancestor -> (diskEntry.size.toFloat() / fromDiskEntry.size.toFloat())
            .takeIf(Float::isFinite)
            ?.times(360f)
            ?: 0f
        else -> 0f
    }
}
