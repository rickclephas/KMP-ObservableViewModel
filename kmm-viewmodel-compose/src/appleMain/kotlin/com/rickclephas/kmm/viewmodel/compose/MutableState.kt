package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.MutableState as ComposeMutableState
import androidx.compose.runtime.mutableStateOf as composeMutableStateOf
import androidx.compose.runtime.SnapshotMutationPolicy
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

public actual interface MutableState<T>: State<T>, ComposeMutableState<T>, MutableStateFlow<T> {
    /**
     * @throws UnsupportedOperationException
     */
    override fun compareAndSet(expect: T, update: T): Nothing = throw UnsupportedOperationException()
    /**
     * @throws UnsupportedOperationException
     */
    @ExperimentalCoroutinesApi
    override fun resetReplayCache(): Nothing = throw UnsupportedOperationException()
}

public actual fun <T : Any?> ViewModelScope.mutableStateOf(
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T> = MutableStateImpl(MutableStateFlow(this, value), policy)

private class MutableStateImpl<T>(
    private val stateFlow: MutableStateFlow<T>,
    private val policy: SnapshotMutationPolicy<T>,
    private val state: ComposeMutableState<T> = composeMutableStateOf(stateFlow.value, policy)
): MutableState<T> {

    override var value: T
        get() = state.value
        set(value) {
            if (policy.equivalent(stateFlow.value, value)) return
            state.value = value
            stateFlow.value = value
        }

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override val subscriptionCount: StateFlow<Int>
        get() = stateFlow.subscriptionCount

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        stateFlow.collect(collector)

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
