package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
@Deprecated(
    message = "Please use the flowOn operator directly",
    replaceWith = ReplaceWith(
        expression = "this.flowOn(coroutineContext).stateIn(viewModelScope, started, initialValue)",
        imports = arrayOf("kotlinx.coroutines.flow.flowOn")
    )
)
public inline fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    coroutineContext: CoroutineContext,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> = flowOn(coroutineContext).stateIn(viewModelScope, started, initialValue)
