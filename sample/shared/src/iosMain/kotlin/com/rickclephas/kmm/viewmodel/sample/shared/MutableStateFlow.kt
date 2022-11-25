package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Suppress("FunctionName")
actual fun <T> MutableStateFlow(viewModelScope: ViewModelScope, value: T): MutableStateFlow<T> {
    val mutableStateFlow = MutableStateFlow(value)
    return MutableStateFlowImpl(viewModelScope as ViewModelScopeImpl, mutableStateFlow)
}

actual fun <T> Flow<T>.stateIn(
    viewModelScope: ViewModelScope,
    started: SharingStarted,
    initialValue: T
): StateFlow<T> {
    val state = MutableStateFlow(viewModelScope, initialValue)
    val start = if (started == SharingStarted.Eagerly) CoroutineStart.DEFAULT else CoroutineStart.UNDISPATCHED
    val job = viewModelScope.scope.launch(start = start) {
        when {
            started === SharingStarted.Eagerly -> {
                // collect immediately & forever
                this@stateIn.collect(state)
            }
            started === SharingStarted.Lazily -> {
                // start collecting on the first subscriber - wait for it first
                state.subscriptionCount.first { it > 0 }
                this@stateIn.collect(state)
            }
            else -> {
                // other & custom strategies
                started.command(state.subscriptionCount)
                    .distinctUntilChanged() // only changes in command have effect
                    .collectLatest { // cancels block on new emission
                        when (it) {
                            SharingCommand.START -> this@stateIn.collect(state) // can be cancelled
                            SharingCommand.STOP -> { /* just cancel and do nothing else */ }
                            SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> state.tryEmit(initialValue)
                        }
                    }
            }
        }
    }
    return ReadonlyStateFlow(state, job)
}

private class ReadonlyStateFlow<T>(
    flow: StateFlow<T>,
    private val job: Job?
): StateFlow<T> by flow


private class MutableStateFlowImpl<T>(
    private val viewModelScope: ViewModelScopeImpl,
    private val stateFlow: MutableStateFlow<T>
): MutableStateFlow<T> by stateFlow {
    override var value: T
        get() = stateFlow.value
        set(value) {
            viewModelScope.sendObjectWillChange()
            stateFlow.value = value
        }

    override fun compareAndSet(expect: T, update: T): Boolean {
        viewModelScope.sendObjectWillChange()
        return stateFlow.compareAndSet(expect, update)
    }

    override fun tryEmit(value: T): Boolean {
        this.value = value
        return true
    }

    override suspend fun emit(value: T) {
        this.value = value
    }

    override val subscriptionCount: StateFlow<Int> =
        SubscriptionCountFlow(stateFlow.subscriptionCount, viewModelScope.subscriptionCount)
}

private class SubscriptionCountFlow(
    private val countOne: StateFlow<Int>,
    private val countTwo: StateFlow<Int>
): StateFlow<Int> {
    override val value: Int
        get() = countOne.value + countTwo.value

    override val replayCache: List<Int>
        get() = listOf(value)

    override suspend fun collect(collector: FlowCollector<Int>): Nothing {
        countOne.combine(countTwo) { countOne, countTwo ->
            countOne + countTwo
        }.collect(collector)
        TODO()
    }
}
