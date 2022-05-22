package com.diskusage.domain.usecases

import com.diskusage.domain.entities.DiskEntry
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class GetRelationship {
    operator fun invoke(diskEntry: DiskEntry, otherDiskEntry: DiskEntry): DiskEntry.Relationship {
        val pathString = diskEntry.path.absolutePathString()
        val otherPathString = otherDiskEntry.path.absolutePathString()
        val siblingsPathStrings = diskEntry.parent?.children
            ?.map(DiskEntry::path)
            ?.map(Path::absolutePathString)
            ?: emptyList()
        return when {
            pathString == otherPathString -> DiskEntry.Relationship.Identity
            otherPathString in siblingsPathStrings -> DiskEntry.Relationship.Sibling
            pathString.startsWith(otherPathString) -> DiskEntry.Relationship.Ancestor
            otherPathString.startsWith(pathString) -> DiskEntry.Relationship.Descendant
            else -> DiskEntry.Relationship.Unrelated
        }
    }
}
