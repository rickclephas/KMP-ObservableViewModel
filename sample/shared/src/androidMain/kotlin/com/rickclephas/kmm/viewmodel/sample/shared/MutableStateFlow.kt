package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.flow.*

@Suppress("FunctionName")
actual fun <T> MutableStateFlow(
    viewModelScope: ViewModelScope,
    value: T
): MutableStateFlow<T> = MutableStateFlow(value)

actual fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = stateIn(viewModelScope.scope, started, initialValue)
