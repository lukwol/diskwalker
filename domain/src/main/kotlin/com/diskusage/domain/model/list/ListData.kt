package com.diskusage.domain.model.list

/**
 * `Parent` [ListItem] and a list of it's [`child items`][ListItem].
 */
data class ListData(
    val parentItem: ListItem,
    val childItems: List<ListItem>
)
