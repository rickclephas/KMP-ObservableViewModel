package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 *
 * On Android this is a subclass of the Jetpack ViewModel.
 */
public actual abstract class KMMViewModel private constructor(coroutineScope: CoroutineScope?) {

    public actual constructor(): this(null)

    public actual constructor(coroutineScope: CoroutineScope): this(coroutineScope as CoroutineScope?)

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     *
     * On Android this is bound to `Dispatchers.Main.immediate`,
     * where on Apple platforms it is bound to `Dispatchers.Main`.
     */
    public actual val viewModelScope: ViewModelScope =
        ViewModelScope(coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.Main))

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual open fun onCleared() {
        viewModelScope.coroutineScope.cancel()
    }
}
