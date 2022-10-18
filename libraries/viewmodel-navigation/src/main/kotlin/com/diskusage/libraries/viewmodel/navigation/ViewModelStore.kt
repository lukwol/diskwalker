package com.diskusage.libraries.viewmodel.navigation

import com.diskusage.libraries.navigation.NavRoute
import com.diskusage.libraries.viewmodel.ViewModel

internal object ViewModelStore {
    internal val viewModels = mutableMapOf<NavRoute, ViewModel>()
}
