package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.darwin.NSObject

/**
 * Implementation of [ViewModelScope] for Apple platforms.
 * @property coroutineScope The [CoroutineScope] associated with the [ViewModel].
 */
internal class NativeViewModelScope internal constructor(
    val coroutineScope: CoroutineScope
): NSObject(), ViewModelScope {

    private val _subscriptionCount = MutableStateFlow(0)
    /**
     * A [StateFlow] that emits the number of subscribers to the [ViewModel].
     */
    val subscriptionCount: StateFlow<Int> = _subscriptionCount.asStateFlow()

    override fun increaseSubscriptionCount() {
        _subscriptionCount.update { it + 1 }
    }

    override fun decreaseSubscriptionCount() {
        _subscriptionCount.update { it - 1 }
    }

    private var sendObjectWillChange: (() -> Unit)? = null

    override fun setSendObjectWillChange(sendObjectWillChange: () -> Unit) {
        if (this.sendObjectWillChange != null) {
            throw IllegalStateException("ViewModel can't be wrapped more than once")
        }
        this.sendObjectWillChange = sendObjectWillChange
    }

    /**
     * Invokes the object will change listener set by [setSendObjectWillChange].
     */
    fun sendObjectWillChange() {
        sendObjectWillChange?.invoke()
    }
}

/**
 * Casts `this` [ViewModelScope] to a [NativeViewModelScope].
 */
internal inline fun ViewModelScope.asNative(): NativeViewModelScope = this as NativeViewModelScope
