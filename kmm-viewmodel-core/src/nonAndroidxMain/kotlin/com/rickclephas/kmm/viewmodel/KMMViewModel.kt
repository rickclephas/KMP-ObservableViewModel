package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public actual abstract class KMMViewModel {

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

@OptIn(ExperimentalStdlibApi::class)
public actual fun KMMViewModel.addCloseable(key: String, closeable: AutoCloseable) {
    closeables[key] = closeable
}

@OptIn(ExperimentalStdlibApi::class)
public actual fun KMMViewModel.addCloseable(closeable: AutoCloseable) {
    closeables += closeable
}

@OptIn(ExperimentalStdlibApi::class)
@Suppress("UNCHECKED_CAST")
public actual fun <T : AutoCloseable> KMMViewModel.getCloseable(key: String): T? =
    closeables[key] as T?
