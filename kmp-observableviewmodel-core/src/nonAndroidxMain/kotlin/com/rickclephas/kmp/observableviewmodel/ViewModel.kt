package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public actual abstract class ViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public actual val viewModelScope: ViewModelScope

    internal val closeables: Closeables

    public actual constructor(): this(DefaultCoroutineScope())

    @OptIn(ExperimentalStdlibApi::class)
    public actual constructor(coroutineScope: CoroutineScope) {
        viewModelScope = ViewModelScope(coroutineScope)
        closeables = Closeables()
    }

    @OptIn(ExperimentalStdlibApi::class)
    public actual constructor(vararg closeables: AutoCloseable): this(DefaultCoroutineScope(), *closeables)

    @OptIn(ExperimentalStdlibApi::class)
    public actual constructor(
        coroutineScope: CoroutineScope,
        vararg closeables: AutoCloseable
    ) {
        viewModelScope = ViewModelScope(coroutineScope)
        this.closeables = Closeables(closeables.toMutableSet())
    }

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual open fun onCleared() { }

    /**
     * Should be called to clear the ViewModel once it's no longer being used.
     */
    public fun clear() {
        viewModelScope.coroutineScope.cancel()
        closeables.close()
        onCleared()
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
public actual fun ViewModel.addCloseable(key: String, closeable: AutoCloseable) {
    closeables[key] = closeable
}

/**
 * Adds an [AutoCloseable] resource to this [ViewModel].
 * The resource will be closed right before the [onCleared][ViewModel.onCleared] method is called.
 *
 * If [onCleared][ViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
@OptIn(ExperimentalStdlibApi::class)
public actual fun ViewModel.addCloseable(closeable: AutoCloseable) {
    closeables += closeable
}

/**
 * Returns the [AutoCloseable] resource associated to the given [key],
 * or `null` if such a [key] is not present in this [ViewModel].
 */
@OptIn(ExperimentalStdlibApi::class)
@Suppress("UNCHECKED_CAST")
public actual fun <T : AutoCloseable> ViewModel.getCloseable(key: String): T? =
    closeables[key] as T?
