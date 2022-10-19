package com.diskusage.libraries.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class MviViewModel<S, C>(initialState: S) : ViewModel() {
    private val state = MutableStateFlow(initialState)

    fun stateAsFlow() = state.asStateFlow()

    fun dispatch(command: C) = viewModelScope.launch { onCommand(command) }

    protected abstract fun onCommand(command: C)

    protected fun <T> withState(block: (S) -> T) = block(state.value)

    protected fun <T> withStateAsync(block: suspend (S) -> T) = viewModelScope.async {
        block(state.value)
    }

    protected fun setState(reducer: S.() -> S) {
        state.value = reducer(state.value)
    }

    protected fun setStateAsync(reducer: suspend S.() -> S) = viewModelScope.launch {
        state.value = reducer(state.value)
    }
}
