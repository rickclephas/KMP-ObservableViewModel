package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

public actual interface ProduceStateScope<T>: MutableState<T>, CoroutineScope {
    /**
     * @throws UnsupportedOperationException
     */
    override val subscriptionCount: Nothing get() = throw UnsupportedOperationException()
    /**
     * @throws UnsupportedOperationException
     */
    override suspend fun collect(collector: FlowCollector<T>): Nothing = throw UnsupportedOperationException()
    /**
     * @throws UnsupportedOperationException
     */
    override fun compareAndSet(expect: T, update: T): Nothing = throw UnsupportedOperationException()
    /**
     * @throws UnsupportedOperationException
     */
    @ExperimentalCoroutinesApi
    override fun resetReplayCache(): Nothing = throw UnsupportedOperationException()

    public actual suspend fun awaitDispose(onDispose: () -> Unit): Nothing
}

public actual fun <T> produceState(
    viewModelScope: ViewModelScope,
    initialValue: T,
    producer: suspend ProduceStateScope<T>.() -> Unit
): State<T> {
    var currentValue = initialValue
    return callbackFlow {
        ProduceStateScopeImpl(viewModelScope.coroutineScope.coroutineContext, { currentValue }, {
            trySend(it)
            currentValue = it
        }, {
            awaitClose(it)
            throw CancellationException()
        }).producer()
    }.conflate().collectAsState(viewModelScope, initialValue)
}

private class ProduceStateScopeImpl<T>(
    override val coroutineContext: CoroutineContext,
    private val getValue: () -> T,
    private val setValue: (T) -> Unit,
    private val awaitDispose: suspend (() -> Unit) -> Nothing
): ProduceStateScope<T> {
    override var value: T
        get() = getValue()
        set(value) = setValue(value)
    override val replayCache: List<T> get() = listOf(getValue())
    override suspend fun awaitDispose(onDispose: () -> Unit): Nothing = awaitDispose.invoke(onDispose)
    override fun component1(): T = getValue()
    override fun component2(): (T) -> Unit = setValue
    override suspend fun emit(value: T) = setValue(value)
    override fun tryEmit(value: T): Boolean {
        setValue(value)
        return true
    }
}
