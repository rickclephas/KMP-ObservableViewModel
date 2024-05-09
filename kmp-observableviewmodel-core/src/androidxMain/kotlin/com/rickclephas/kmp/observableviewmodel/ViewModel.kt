package com.rickclephas.kmp.observableviewmodel

import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public actual abstract class ViewModel: AndroidXViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public actual val viewModelScope: ViewModelScope

    public actual constructor(): this(DefaultCoroutineScope())

    public actual constructor(coroutineScope: CoroutineScope): super(coroutineScope) {
        viewModelScope = ViewModelScope(coroutineScope)
    }

    @OptIn(ExperimentalStdlibApi::class)
    public actual constructor(vararg closeables: AutoCloseable): this(DefaultCoroutineScope(), *closeables)

    @OptIn(ExperimentalStdlibApi::class)
    public actual constructor(
        coroutineScope: CoroutineScope,
        vararg closeables: AutoCloseable
    ): super(coroutineScope, *closeables) {
        viewModelScope = ViewModelScope(coroutineScope)
    }

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
    @InternalKMPObservableViewModelApi
    public fun clear() {
        // We can't directly call the internal clear function from AndroidX.
        // To call it indirectly we use the public Store and Provider APIs instead.
        val store = ViewModelStore()
        ViewModelProvider.create(
            store = store,
            factory = object : ViewModelProvider.Factory {
                override fun <T : AndroidXViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                    @Suppress("UNCHECKED_CAST")
                    return this@ViewModel as T
                }
            }
        )[ViewModel::class]
        store.clear()
    }
}

/**
 * Adds an [AutoCloseable] resource with an associated [key] to this [ViewModel].
 * The resource will be closed right before the [onCleared][ViewModel.onCleared] method is called.
 *
 * If the [key] already has a resource associated with it, the old resource will be replaced and closed immediately.
 *
 * If [onCleared][ViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
@OptIn(ExperimentalStdlibApi::class)
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
public actual inline fun ViewModel.addCloseable(key: String, closeable: AutoCloseable): Unit =
    addCloseable(key, closeable)

/**
 * Adds an [AutoCloseable] resource to this [ViewModel].
 * The resource will be closed right before the [onCleared][ViewModel.onCleared] method is called.
 *
 * If [onCleared][ViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
@OptIn(ExperimentalStdlibApi::class)
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
public actual inline fun ViewModel.addCloseable(closeable: AutoCloseable): Unit =
    addCloseable(closeable)

/**
 * Returns the [AutoCloseable] resource associated to the given [key],
 * or `null` if such a [key] is not present in this [ViewModel].
 */
@OptIn(ExperimentalStdlibApi::class)
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
public actual inline fun <T : AutoCloseable> ViewModel.getCloseable(key: String): T? =
    getCloseable(key)
