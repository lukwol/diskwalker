package com.diskusage.libraries.viewmodel.screens.navigation

import io.github.lukwol.screen.navigation.ScreenRoute
import io.github.lukwol.viewmodel.ViewModel

/**
 * View model store is responsible for keeping [view models][ViewModel] state,
 * for screens that are not currently displayed but are on the stack.
 *
 * @property viewModels underlying mutable map
 * that registers [view models][ViewModel] for [routes][ScreenRoute]
 */
internal class ViewModelStore private constructor(
    private val viewModels: MutableMap<ScreenRoute, ViewModel>
) : MutableMap<ScreenRoute, ViewModel> by viewModels {
    constructor() : this(mutableMapOf())
}
