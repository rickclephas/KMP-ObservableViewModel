package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.CoroutineScope

public expect interface ProduceStateScope<T>: MutableState<T>, CoroutineScope {
    public suspend fun awaitDispose(onDispose: () -> Unit): Nothing
}

public expect fun <T> produceState(
    viewModelScope: ViewModelScope,
    initialValue: T,
    producer: suspend ProduceStateScope<T>.() -> Unit
): State<T>
