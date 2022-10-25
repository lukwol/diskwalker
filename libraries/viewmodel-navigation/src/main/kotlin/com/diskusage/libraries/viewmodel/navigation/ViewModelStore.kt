package com.diskusage.libraries.viewmodel.navigation

import com.diskusage.libraries.viewmodel.ViewModel

internal class ViewModelStore private constructor(
    private val viewModels: MutableMap<CombinedRoute, ViewModel>
) : MutableMap<CombinedRoute, ViewModel> by viewModels {
    constructor() : this(mutableMapOf())

    fun remove(windowRoute: String) = viewModels.keys.filter {
        it.windowRoute == windowRoute
    }.forEach {
        viewModels.remove(it)
    }
}
