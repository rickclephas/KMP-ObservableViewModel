package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = stateIn(viewModelScope, EmptyCoroutineContext, started, initialValue)

public fun <T, R> StateFlow<T>.map(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    transform: (T) -> R
): StateFlow<R> = map { transform(it) }.stateIn(viewModelScope, started, transform(value))
