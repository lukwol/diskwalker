package com.diskusage.domain.model

/**
 * List items collection with `parent` [ListItem] and list of it's [`child list items`][ListItem].
 */
data class ListItemsCollection(
    val parentItem: ListItem,
    val childItems: List<ListItem>
)
