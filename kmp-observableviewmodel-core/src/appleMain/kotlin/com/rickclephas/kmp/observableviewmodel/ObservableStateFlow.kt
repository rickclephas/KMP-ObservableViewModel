package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [MutableStateFlow] wrapper that emits state change events through the [NativeViewModelScope]
 * and it accounts for the [NativeViewModelScope.subscriptionCount].
 */
@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
internal class ObservableMutableStateFlow<T>(
    private val viewModelScope: NativeViewModelScope,
    private val stateFlow: MutableStateFlow<T>
): MutableStateFlow<T> {

    private val property = StateFlowProperty(stateFlow)

    override var value: T
        get() {
            viewModelScope.publisher?.access(property)
            return stateFlow.value
        }
        set(value) {
            val publisher = viewModelScope.publisher?.takeIf { stateFlow.value != value }
            publisher?.willSet(property)
            stateFlow.value = value
            publisher?.didSet(property)
        }

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    /**
     * The combined subscription count from the [NativeViewModelScope] and the actual [StateFlow].
     */
    override val subscriptionCount: StateFlow<Int> = CombinedSubscriptionCount(viewModelScope, stateFlow)

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        stateFlow.collect(collector)

    override fun compareAndSet(expect: T, update: T): Boolean {
        val publisher = viewModelScope.publisher?.takeIf { stateFlow.value == expect && expect != update }
        publisher?.willSet(property)
        val result = stateFlow.compareAndSet(expect, update)
        publisher?.didSet(property)
        return result
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = stateFlow.resetReplayCache()

    // Same implementation as in StateFlowImpl, but we need to go through our own value property.
    // https://github.com/Kotlin/kotlinx.coroutines/blob/6dfabf763fe9fc91fbb73eb0f2d5b488f53043f1/kotlinx-coroutines-core/common/src/flow/StateFlow.kt#L369
    override fun tryEmit(value: T): Boolean {
        this.value = value
        return true
    }

    // Same implementation as in StateFlowImpl, but we need to go through our own value property.
    // https://github.com/Kotlin/kotlinx.coroutines/blob/6dfabf763fe9fc91fbb73eb0f2d5b488f53043f1/kotlinx-coroutines-core/common/src/flow/StateFlow.kt#L374
    override suspend fun emit(value: T) {
        this.value = value
    }
}

/**
 * A [StateFlow] backed by an [ObservableMutableStateFlow] optionally holding a reference to a [Job].
 */
@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
internal class ObservableStateFlow<T>(
    internal val flow: ObservableMutableStateFlow<T>,
    @Suppress("unused")
    private val job: Job? = null
): StateFlow<T> by flow
