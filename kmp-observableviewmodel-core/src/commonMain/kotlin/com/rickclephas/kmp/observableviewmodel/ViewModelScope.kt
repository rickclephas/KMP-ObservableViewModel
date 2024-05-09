package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * Holds the [CoroutineScope] of a [KMMViewModel].
 * @see coroutineScope
 */
public expect interface ViewModelScope

/**
 * Creates a new [ViewModelScope] for the provided [coroutineScope].
 */
internal expect fun ViewModelScope(coroutineScope: CoroutineScope): ViewModelScope

/**
 * Gets the [CoroutineScope] associated with the [KMMViewModel] of `this` [ViewModelScope].
 */
public expect val ViewModelScope.coroutineScope: CoroutineScope
