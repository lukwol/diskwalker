package com.diskusage.domain.model

// TODO: Add documentation

data class ListItemsCollection(
    val selectedItem: ListItem,
    val childItems: List<ListItem>
)
