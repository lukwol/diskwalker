package com.diskusage.domain.entities

import androidx.compose.ui.graphics.Color

/**
 * Represents item in entries list
 *
 * @property diskEntry contains item data
 * @property color [Color] assigned to [diskEntry]
 */
data class ListItem(
    val diskEntry: DiskEntry,
    var color: Color
)
