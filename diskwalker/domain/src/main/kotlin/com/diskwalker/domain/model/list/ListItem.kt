package com.diskwalker.domain.model.list

import androidx.compose.ui.graphics.Color
import com.diskwalker.domain.model.path.PathInfo
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
    var color: Color,
)
