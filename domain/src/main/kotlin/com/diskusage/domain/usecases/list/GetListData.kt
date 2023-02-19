package com.diskusage.domain.usecases.list

import com.diskusage.domain.model.ListData
import com.diskusage.domain.usecases.diskentry.IncludeDiskEntry
import com.diskusage.domain.usecases.diskentry.SortDiskEntries
import com.diskusage.domain.usecases.list.item.GetListItem
import com.diskusage.domain.usecases.scan.GetChildren
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path

/**
 * Get [ListData] with [selectedItem][ListData.parentItem]
 * and it's [childItems][ListData.childItems].
 *
 * [ChildItems][ListData.childItems] are sorted and have smaller items filtered out.
 *
 * @see sortDiskEntries
 */
class GetListData(
    private val sortDiskEntries: SortDiskEntries,
    private val getListItem: GetListItem,
    private val includeDiskEntry: IncludeDiskEntry,
    private val getChildren: GetChildren
) {
    suspend operator fun invoke(
        path: Path,
        fromPath: Path = path
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getListItem(path, fromPath),
            childItems = getChildren(path)
                .filter { includeDiskEntry(it, path) }
                .let(sortDiskEntries::invoke)
                .map { getListItem(it, fromPath) }
        )
    }
}
