package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @see kotlinx.coroutines.flow.MutableStateFlow
 */
public actual fun <T> MutableStateFlow(
    viewModelScope: ViewModelScope,
    value: T
): MutableStateFlow<T> = ObservableMutableStateFlow(viewModelScope.asNative(), MutableStateFlow(value))

/**
 * @see kotlinx.coroutines.flow.stateIn
 */
public actual fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> {
    // Similar to kotlinx.coroutines, but using our custom MutableStateFlowImpl and CoroutineContext logic.
    // https://github.com/Kotlin/kotlinx.coroutines/blob/6dfabf763fe9fc91fbb73eb0f2d5b488f53043f1/kotlinx-coroutines-core/common/src/flow/operators/Share.kt#L135
    val scope = viewModelScope.asNative()
    val state = ObservableMutableStateFlow(scope, MutableStateFlow(initialValue))
    val job = scope.coroutineScope.launchSharing(EmptyCoroutineContext, this, state, started, initialValue)
    return ObservableStateFlow(state, job)
}

/**
 * Identical to the kotlinx.coroutines implementation, but without the SharedFlow logic.
 * https://github.com/Kotlin/kotlinx.coroutines/blob/6dfabf763fe9fc91fbb73eb0f2d5b488f53043f1/kotlinx-coroutines-core/common/src/flow/operators/Share.kt#L194
 */
private fun <T> CoroutineScope.launchSharing(
    context: CoroutineContext,
    upstream: Flow<T>,
    shared: MutableSharedFlow<T>,
    started: SharingStarted,
    initialValue: T
): Job {
    val start = if (started == SharingStarted.Eagerly) CoroutineStart.DEFAULT else CoroutineStart.UNDISPATCHED
    return launch(context, start = start) {
        when {
            started === SharingStarted.Eagerly -> {
                upstream.collect(shared)
            }
            started === SharingStarted.Lazily -> {
                shared.subscriptionCount.first { it > 0 }
                upstream.collect(shared)
            }
            else -> started.command(shared.subscriptionCount)
                .distinctUntilChanged()
                .collectLatest {
                    when (it) {
                        SharingCommand.START -> upstream.collect(shared)
                        SharingCommand.STOP -> { }
                        SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> shared.tryEmit(initialValue)
                    }
                }
        }
    }
}
