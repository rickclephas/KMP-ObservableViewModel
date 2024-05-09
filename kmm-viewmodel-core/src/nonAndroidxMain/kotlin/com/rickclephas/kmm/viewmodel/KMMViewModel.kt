package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public actual abstract class KMMViewModel public actual constructor(coroutineScope: CoroutineScope) {

    public actual constructor(): this(DefaultCoroutineScope())

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public actual val viewModelScope: ViewModelScope = ViewModelScope(coroutineScope)

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual open fun onCleared() { }

    /**
     * Should be called to clear the ViewModel once it's no longer being used.
     */
    public fun clear() {
        viewModelScope.coroutineScope.cancel()
        onCleared()
    }
}
