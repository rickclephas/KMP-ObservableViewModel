package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * Holds the [CoroutineScope] of a [ViewModel].
 * @see coroutineScope
 */
public actual interface ViewModelScope

/**
 * Creates a new [ViewModelScope] for the provided [coroutineScope].
 */
internal actual fun ViewModelScope(coroutineScope: CoroutineScope): ViewModelScope =
    ViewModelScopeImpl(coroutineScope)

/**
 * Gets the [CoroutineScope] associated with the [ViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = (this as ViewModelScopeImpl).coroutineScope

private class ViewModelScopeImpl(val coroutineScope: CoroutineScope): ViewModelScope
