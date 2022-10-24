package com.diskusage.libraries.viewmodel.navigation

import com.diskusage.libraries.viewmodel.ViewModel

internal class ViewModelStore {
    val viewModels = mutableMapOf<WindowRoute, ViewModel>()
}
