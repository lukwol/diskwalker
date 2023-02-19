package com.diskusage.domain.model.list

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.path.PathInfo
import java.nio.file.Path

/**
 * Represents item in entries list
 *
 * @property path contains item data
 * @property color [Color] assigned to [path]
 */
data class ListItem(
    val path: Path,
    val pathInfo: PathInfo,
    var color: Color
)
