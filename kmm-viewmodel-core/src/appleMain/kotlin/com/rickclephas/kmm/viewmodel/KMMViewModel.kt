package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlin.native.ref.WeakReference

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
    @Suppress("LeakingThis")
    public actual val viewModelScope: ViewModelScope = ViewModelScopeImpl(WeakReference(this))

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual open fun onCleared() { }
}
