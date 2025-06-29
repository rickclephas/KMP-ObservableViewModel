package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMViewModelKeyPathProtocol
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

    /**
     * The [KeyPath][KMPOVMViewModelKeyPathProtocol] associated with the [StateFlow] property.
     */
    var keyPath: KMPOVMViewModelKeyPathProtocol? = null
        set(value) {
            if (value != null) viewModelScope.requireKeyPaths = true
            field = value
        }

    override var value: T
        get() = viewModelScope.access(keyPath) { stateFlow.value }
        set(value) {
            val changed = stateFlow.value != value
            viewModelScope.set(keyPath, changed) {
                stateFlow.value = value
            }
        }

    // Same implementation as in StateFlowImpl, but we need to go through our own value property.
    // https://github.com/Kotlin/kotlinx.coroutines/blob/6dfabf763fe9fc91fbb73eb0f2d5b488f53043f1/kotlinx-coroutines-core/common/src/flow/StateFlow.kt#L367
    override val replayCache: List<T>
        get() = listOf(value)

    /**
     * The combined subscription count from the [NativeViewModelScope] and the actual [StateFlow].
     */
    override val subscriptionCount: StateFlow<Int> = CombinedSubscriptionCount(viewModelScope, stateFlow)

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        stateFlow.collect(collector)

    override fun compareAndSet(expect: T, update: T): Boolean {
        val changed = stateFlow.value == expect && expect != update
        return viewModelScope.set(keyPath, changed) {
            stateFlow.compareAndSet(expect, update)
        }
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

/**
 * Returns the `ObservableStateFlow` for the provided [stateFlow].
 * @throws IllegalArgumentException if the [stateFlow] isn't an `ObservableStateFlow`.
 */
internal fun <T> requireObservableStateFlow(
    stateFlow: StateFlow<T>,
): ObservableMutableStateFlow<T> = when (stateFlow) {
    is ObservableMutableStateFlow -> stateFlow
    is ObservableStateFlow -> stateFlow.flow
    else -> throw IllegalArgumentException("$stateFlow is not an ObservableStateFlow")
}

/**
 * Asserts that the provided [stateFlow] is an `ObservableStateFlow`.
 * @throws IllegalArgumentException if the [stateFlow] isn't an `ObservableStateFlow`.
 */
@InternalKMPObservableViewModelApi
public fun <T> assertObservableStateFlow(stateFlow: StateFlow<T>) {
    requireObservableStateFlow(stateFlow)
}
