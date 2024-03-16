package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

/**
 * @see kotlinx.coroutines.flow.MutableStateFlow
 */
public actual inline fun <T> MutableStateFlow(
    viewModelScope: ViewModelScope,
    value: T
): MutableStateFlow<T> = MutableStateFlow(value)

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
public actual inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    coroutineContext: CoroutineContext,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = flowOn(coroutineContext).stateIn(viewModelScope.coroutineScope, started, initialValue)
