package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = stateIn(viewModelScope, viewModelScope.coroutineScope, started, initialValue)

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    context: CoroutineContext,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = stateIn(viewModelScope, viewModelScope.coroutineScope + context, started, initialValue)
