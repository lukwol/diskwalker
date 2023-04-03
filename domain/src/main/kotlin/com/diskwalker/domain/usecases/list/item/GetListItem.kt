package com.diskwalker.domain.usecases.list.item

import com.diskwalker.domain.model.list.ListItem
import com.diskwalker.domain.usecases.chart.item.GetColor
import com.diskwalker.domain.usecases.path.GetPathInfo
import java.nio.file.Path

/**
 * Creates [ListItem] for given `path` starting from `fromPath`.
 */
class GetListItem(
    private val getColor: GetColor,
    private val getPathInfo: GetPathInfo,
) {

    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
    ) = ListItem(
        path = path,
        pathInfo = getPathInfo(path),
        color = getColor(path, fromPath, disk),
    )
}
