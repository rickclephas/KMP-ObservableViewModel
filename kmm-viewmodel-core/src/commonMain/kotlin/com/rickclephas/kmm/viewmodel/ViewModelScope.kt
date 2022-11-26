package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * Holds the [CoroutineScope] of a [KMMViewModel].
 * @see coroutineScope
 */
public expect interface ViewModelScope

/**
 * Gets the [CoroutineScope] associated with the [KMMViewModel] of `this` [ViewModelScope].
 */
public expect val ViewModelScope.coroutineScope: CoroutineScope
