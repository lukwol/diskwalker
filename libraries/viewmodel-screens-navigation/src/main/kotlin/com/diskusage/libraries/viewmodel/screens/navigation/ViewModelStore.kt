package com.diskusage.libraries.viewmodel.screens.navigation

import com.diskusage.libraries.screens.navigation.ScreenRoute
import com.diskusage.libraries.viewmodel.ViewModel

internal class ViewModelStore private constructor(
    private val viewModels: MutableMap<ScreenRoute, ViewModel>
) : MutableMap<ScreenRoute, ViewModel> by viewModels {
    constructor() : this(mutableMapOf())
}
