package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

public expect fun <T : R, R> Flow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext
): State<R>

public expect fun <T : R, R> StateFlow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    context: CoroutineContext = EmptyCoroutineContext
): State<R>
