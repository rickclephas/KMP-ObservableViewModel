package com.rickclephas.kmm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

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

internal class ViewModelScopeImpl(viewModel: KMMViewModel): ViewModelScope {
    val coroutineScope: CoroutineScope = (viewModel as ViewModel).viewModelScope
}
