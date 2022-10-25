package com.diskusage.libraries.viewmodel.navigation

import com.diskusage.libraries.viewmodel.ViewModel

internal class ViewModelStore private constructor(
    private val viewModels: MutableMap<String, ViewModel>
) : MutableMap<String, ViewModel> by viewModels {
    constructor() : this(mutableMapOf())
}
