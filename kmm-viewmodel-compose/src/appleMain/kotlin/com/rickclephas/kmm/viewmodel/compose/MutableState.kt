package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

public actual interface MutableState<T>: State<T>, MutableStateFlow<T> {
    public actual override var value: T
    public actual operator fun component1(): T
    public actual operator fun component2(): (T) -> Unit
}

public actual fun <T : Any?> mutableStateOf(
    viewModelScope: ViewModelScope,
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T> = MutableStateImpl(MutableStateFlow(viewModelScope, value), policy)

private class MutableStateImpl<T>(
    private val stateFlow: MutableStateFlow<T>,
    private val policy: SnapshotMutationPolicy<T>
): MutableState<T> {

    override var value: T
        get() = stateFlow.value
        set(value) {
            if (policy.equivalent(stateFlow.value, value)) return
            stateFlow.value = value
        }

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override val subscriptionCount: StateFlow<Int>
        get() = stateFlow.subscriptionCount

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        stateFlow.collect(collector)

    override fun compareAndSet(expect: T, update: T): Boolean {
        return stateFlow.compareAndSet(expect, if (policy.equivalent(expect, update)) expect else update)
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

    override fun component1(): T = value

    override fun component2(): (T) -> Unit = { value = it }
}
