package com.diskusage.libraries.viewmodel.navigation

import com.diskusage.libraries.navigation.screens.ScreenRoute
import com.diskusage.libraries.viewmodel.ViewModel

internal class ViewModelStore private constructor(
    private val viewModels: MutableMap<ScreenRoute, ViewModel>
) : MutableMap<ScreenRoute, ViewModel> by viewModels {
    constructor() : this(mutableMapOf())
}
