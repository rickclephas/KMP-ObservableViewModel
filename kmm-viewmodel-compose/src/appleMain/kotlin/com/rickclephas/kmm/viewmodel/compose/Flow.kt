package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

public actual fun <T : R, R> Flow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    initial: R,
    context: CoroutineContext
): State<R> = object : State<R>, StateFlow<R> by stateIn(viewModelScope, context, SharingStarted.WhileSubscribed(), initial) { }

public actual fun <T : R, R> StateFlow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    context: CoroutineContext
): State<R> = collectAsState(viewModelScope, value, context)
