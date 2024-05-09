package com.rickclephas.kmm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public actual abstract class KMMViewModel public actual constructor(
    coroutineScope: CoroutineScope
): ViewModel(coroutineScope) {

    public actual constructor(): this(DefaultCoroutineScope())

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public actual val viewModelScope: ViewModelScope = ViewModelScope(coroutineScope)

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual override fun onCleared() {
        super.onCleared()
    }

    /**
     * Internal KMM-ViewModel function used by the Swift implementation to clear the ViewModel.
     * Warning: you should NOT call this yourself!
     */
    @InternalKMMViewModelApi
    public fun clear() {
        // We can't directly call the internal clear function from AndroidX.
        // To call it indirectly we use the public Store and Provider APIs instead.
        val store = ViewModelStore()
        ViewModelProvider.create(
            store = store,
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                    @Suppress("UNCHECKED_CAST")
                    return this@KMMViewModel as T
                }
            }
        )[KMMViewModel::class]
        store.clear()
    }
}
