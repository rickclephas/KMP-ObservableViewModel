package com.rickclephas.kmm.viewmodel.savedstate

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public inline fun <reified T> SavedStateHandle.getMutableStateFlow(
    viewModelScope: ViewModelScope,
    key: String,
    initialValue: T
): MutableStateFlow<T> = getStateFlow(viewModelScope, key, initialValue).asMutableStateFlow { set(key, it) }

@PublishedApi
internal fun <T> StateFlow<T>.asMutableStateFlow(setValue: (T) -> Unit): MutableStateFlow<T> =
    MutableStateFlowImpl(this, setValue)

private class MutableStateFlowImpl<T>(
    private val stateFlow: StateFlow<T>,
    private val setValue: (T) -> Unit,
): MutableStateFlow<T>, StateFlow<T> by stateFlow {

    override val subscriptionCount: StateFlow<Int>
        get() = throw UnsupportedOperationException("SavedStateHandle MutableStateFlow.subscriptionCount is not supported")

    override var value: T
        get() = stateFlow.value
        set(value) { setValue(value) }

    override fun compareAndSet(expect: T, update: T): Boolean {
        value = update
        return true
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        throw UnsupportedOperationException("MutableStateFlow.resetReplayCache is not supported")
    }

    override fun tryEmit(value: T): Boolean {
        this.value = value
        return true
    }

    override suspend fun emit(value: T) {
        this.value = value
    }
}
