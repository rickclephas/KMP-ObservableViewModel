package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * A Kotlin Multiplatform ViewModel.
 */
public expect abstract class ViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public val viewModelScope: ViewModelScope

    public constructor()

    public constructor(coroutineScope: CoroutineScope)

    public constructor(vararg closeables: AutoCloseable)

    public constructor(coroutineScope: CoroutineScope, vararg closeables: AutoCloseable)

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public open fun onCleared()
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
public expect fun ViewModel.addCloseable(key: String, closeable: AutoCloseable)

/**
 * Adds an [AutoCloseable] resource to this [ViewModel].
 * The resource will be closed right before the [onCleared][ViewModel.onCleared] method is called.
 *
 * If [onCleared][ViewModel.onCleared] has already been called,
 * the provided resource will not be added and will be closed immediately.
 */
public expect fun ViewModel.addCloseable(closeable: AutoCloseable)

/**
 * Returns the [AutoCloseable] resource associated to the given [key],
 * or `null` if such a [key] is not present in this [ViewModel].
 */
public expect fun <T : AutoCloseable> ViewModel.getCloseable(key: String): T?
