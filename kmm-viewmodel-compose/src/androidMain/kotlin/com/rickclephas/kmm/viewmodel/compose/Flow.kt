package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import androidx.compose.runtime.collectAsState as collectAsStateImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : R, R> Flow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    initial: R,
    context: CoroutineContext
): State<R> = collectAsStateImpl(initial, context)

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : R, R> StateFlow<T>.collectAsState(
    viewModelScope: ViewModelScope,
    context: CoroutineContext
): State<R> = collectAsStateImpl(context)
