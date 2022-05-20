package com.diskusage.domain.usecases

import com.diskusage.domain.common.Constants
import com.diskusage.domain.entities.DiskEntry

class GetDiskEntries(private val getDepth: GetDepth) {

    operator fun invoke(diskEntry: DiskEntry) = diskEntriesList(diskEntry)
        .filter { (it.size.toFloat() / diskEntry.size.toFloat()) >= Constants.DiskEntrySizeFilterThreshold }
        .filter { (getDepth(it, diskEntry) <= Constants.MaxChartDepth) }

    private fun diskEntriesList(diskEntry: DiskEntry): List<DiskEntry> =
        listOf(diskEntry) + when (diskEntry) {
            is DiskEntry.Directory -> diskEntry.children.flatMap(::diskEntriesList)
            else -> listOf()
        }
}
