package com.diskwalker.domain.usecases.list

import com.diskwalker.domain.model.list.ListData
import com.diskwalker.domain.usecases.list.item.GetListItem
import com.diskwalker.domain.usecases.path.IncludePath
import com.diskwalker.domain.usecases.path.SortPaths
import com.diskwalker.domain.usecases.scan.GetChildren
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.nio.file.Path

/**
 * Get [ListData] with [selectedItem][ListData.parentItem]
 * and it's [childItems][ListData.childItems].
 *
 * [ChildItems][ListData.childItems] are sorted and have smaller items filtered out.
 *
 * @see sortPaths
 */
class GetListData(
    private val sortPaths: SortPaths,
    private val getListItem: GetListItem,
    private val includePath: IncludePath,
    private val getChildren: GetChildren,
) {
    suspend operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path = fromPath,
    ) = withContext(Dispatchers.Default) {
        ListData(
            parentItem = getListItem(path, fromPath, disk),
            childItems = getChildren(path)
                .filter { includePath(it, path, disk) }
                .let(sortPaths::invoke)
                .map { async { getListItem(it, fromPath, disk) } }
                .awaitAll(),
        )
    }
}
