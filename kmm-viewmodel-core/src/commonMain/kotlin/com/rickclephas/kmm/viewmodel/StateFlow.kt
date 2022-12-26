package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

/**
 * @see kotlinx.coroutines.flow.MutableStateFlow
 */
public expect fun <T> MutableStateFlow(
    viewModelScope: ViewModelScope,
    value: T
): MutableStateFlow<T>

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
public expect fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    coroutineContext: CoroutineContext,
    started: SharingStarted,
    initialValue: T
): StateFlow<T>
