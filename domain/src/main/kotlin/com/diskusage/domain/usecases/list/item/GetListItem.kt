package com.diskusage.domain.usecases.list.item

import com.diskusage.domain.model.ListItem
import com.diskusage.domain.usecases.chart.item.GetColor
import com.diskusage.domain.usecases.scan.GetPathInfo
import java.nio.file.Path

/**
 * Creates [ListItem] for given `diskEntry` starting from `fromDiskEntry`.
 */
class GetListItem(
    private val getColor: GetColor,
    private val getPathInfo: GetPathInfo
) {

    operator fun invoke(
        path: Path,
        fromPath: Path = path
    ) = ListItem(
        path = path,
        pathInfo = getPathInfo(path),
        color = getColor(path, fromPath)
    )
}
