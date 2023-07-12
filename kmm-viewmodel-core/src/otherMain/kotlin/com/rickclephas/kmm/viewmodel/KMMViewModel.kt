package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 *
 * On Android this is a subclass of the Jetpack ViewModel.
 */
public actual abstract class KMMViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     *
     * On Android this is bound to `Dispatchers.Main.immediate`,
     * where on Apple platforms it is bound to `Dispatchers.Main`.
     */
    public actual val viewModelScope: ViewModelScope = ViewModelScopeImpl()

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual open fun onCleared() {
        viewModelScope.coroutineScope.cancel()
    }
}
