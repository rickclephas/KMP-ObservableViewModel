package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public expect abstract class KMMViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public val viewModelScope: ViewModelScope

    public constructor()

    public constructor(coroutineScope: CoroutineScope)

    @OptIn(ExperimentalStdlibApi::class)
    public constructor(vararg closeables: AutoCloseable)

    @OptIn(ExperimentalStdlibApi::class)
    public constructor(coroutineScope: CoroutineScope, vararg closeables: AutoCloseable)

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public open fun onCleared()
}

/**
 * Adds an [AutoCloseable] resource with an associated [key] to this [KMMViewModel].
 * The resource will be closed right before the [onCleared][KMMViewModel.onCleared] method is called.
 *
 * If the [key] already has a resource associated with it, the old resource will be replaced and closed immediately.
 *
 * If [onCleared][KMMViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
@OptIn(ExperimentalStdlibApi::class)
public expect fun KMMViewModel.addCloseable(key: String, closeable: AutoCloseable)

/**
 * Adds an [AutoCloseable] resource to this [KMMViewModel].
 * The resource will be closed right before the [onCleared][KMMViewModel.onCleared] method is called.
 *
 * If [onCleared][KMMViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
@OptIn(ExperimentalStdlibApi::class)
public expect fun KMMViewModel.addCloseable(closeable: AutoCloseable)

/**
 * Returns the [AutoCloseable] resource associated to the given [key],
 * or `null` if such a [key] is not present in this [KMMViewModel].
 */
@OptIn(ExperimentalStdlibApi::class)
public expect fun <T : AutoCloseable> KMMViewModel.getCloseable(key: String): T?
