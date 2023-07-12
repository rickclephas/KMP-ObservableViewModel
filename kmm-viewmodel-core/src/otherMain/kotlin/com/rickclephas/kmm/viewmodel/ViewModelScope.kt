package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Holds the [CoroutineScope] of a [KMMViewModel].
 * @see coroutineScope
 */
public actual interface ViewModelScope

/**
 * Gets the [CoroutineScope] associated with the [KMMViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = (this as ViewModelScopeImpl).coroutineScope

internal class ViewModelScopeImpl: ViewModelScope {
    val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}
