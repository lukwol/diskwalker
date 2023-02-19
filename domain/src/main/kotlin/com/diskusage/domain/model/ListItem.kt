package com.diskusage.domain.model

import androidx.compose.ui.graphics.Color
import com.diskusage.domain.model.scan.ScanItem
import java.nio.file.Path

/**
 * Represents item in entries list
 *
 * @property diskEntry contains item data
 * @property color [Color] assigned to [diskEntry]
 */
data class ListItem(
    val path: Path,
    val scanItem: ScanItem,
    var color: Color
)
