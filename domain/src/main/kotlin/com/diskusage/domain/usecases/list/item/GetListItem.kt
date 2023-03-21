package com.diskusage.domain.usecases.list.item

import com.diskusage.domain.model.list.ListItem
import com.diskusage.domain.usecases.chart.item.GetColor
import com.diskusage.domain.usecases.path.GetPathInfo
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
        fromPath: Path = path,
        disk: Path,
    ) = ListItem(
        path = path,
        pathInfo = getPathInfo(path),
        color = getColor(path, fromPath, disk),
    )
}
