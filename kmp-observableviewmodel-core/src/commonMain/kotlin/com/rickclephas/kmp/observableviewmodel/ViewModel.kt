package com.rickclephas.kmp.observableviewmodel

import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

/**
 * A Kotlin Multiplatform ViewModel.
 */
public abstract class ViewModel: AndroidXViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public val viewModelScope: ViewModelScope

    public constructor(): this(DefaultCoroutineScope())

    public constructor(coroutineScope: CoroutineScope): super(coroutineScope) {
        viewModelScope = ViewModelScope(coroutineScope)
    }

    public constructor(vararg closeables: AutoCloseable): this(DefaultCoroutineScope(), *closeables)

    public constructor(
        coroutineScope: CoroutineScope,
        vararg closeables: AutoCloseable
    ): super(coroutineScope, *closeables) {
        viewModelScope = ViewModelScope(coroutineScope)
    }

    /**
     * Internal KMP-ObservableViewModel function used by the Swift implementation to clear the ViewModel.
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

@Suppress("EXTENSION_SHADOWED_BY_MEMBER", "NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")
@Deprecated("Use addCloseable on AndroidX ViewModel directly")
public inline fun ViewModel.addCloseable(key: String, closeable: AutoCloseable): Unit =
    addCloseable(key, closeable)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER", "NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")
@Deprecated("Use addCloseable on AndroidX ViewModel directly")
public inline fun ViewModel.addCloseable(closeable: AutoCloseable): Unit =
    addCloseable(closeable)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER", "NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")
@Deprecated("Use getCloseable on AndroidX ViewModel directly")
public inline fun <T : AutoCloseable> ViewModel.getCloseable(key: String): T? =
    getCloseable(key)
