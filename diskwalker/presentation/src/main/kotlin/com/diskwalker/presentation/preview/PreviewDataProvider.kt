package com.diskwalker.presentation.preview

import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.domain.model.disk.DiskType
import kotlin.io.path.Path

@Suppress("MemberVisibilityCanBePrivate")
object PreviewDataProvider {
    object DiskInfo {
        val macintoshHd = DiskInfo(
            name = "Macintosh HD",
            mountPoint = Path("/"),
            totalSpace = 494384795648,
            availableSpace = 235093038035,
            takenSpace = 259291757613,
            isRemovable = false,
            diskType = DiskType.SSD,
            fileSystem = "apfs",
        )

        val macintoshHdData = DiskInfo(
            name = "Macintosh HD",
            mountPoint = Path("/System/Volumes/Data"),
            totalSpace = 494384795648,
            availableSpace = 234764567507,
            takenSpace = 259620228141,
            isRemovable = false,
            diskType = DiskType.SSD,
            fileSystem = "apfs",
        )

        val sony = DiskInfo(
            name = "SONY64GB",
            mountPoint = Path("/Volumes/SONY64GB"),
            totalSpace = 62143299584,
            availableSpace = 27657699328,
            takenSpace = 34485600256,
            isRemovable = true,
            diskType = DiskType.HDD,
            fileSystem = "msdos",
        )

        val pja = DiskInfo(
            name = "PJA-8GB",
            mountPoint = Path("/Volumes/PJA-8GB"),
            totalSpace = 8033161216,
            availableSpace = 7699701760,
            takenSpace = 333459456,
            isRemovable = true,
            diskType = DiskType.HDD,
            fileSystem = "msdos",
        )

        val allDisksInfo = listOf(macintoshHd, macintoshHdData, sony, pja)
    }
}
