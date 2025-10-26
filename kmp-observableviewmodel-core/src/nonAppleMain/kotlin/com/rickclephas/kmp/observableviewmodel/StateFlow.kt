package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.flow.*

/**
 * @see kotlinx.coroutines.flow.MutableStateFlow
 */
@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> MutableStateFlow(
    viewModelScope: ViewModelScope,
    value: T
): MutableStateFlow<T> = MutableStateFlow(value)

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = stateIn(viewModelScope.coroutineScope, started, initialValue)
