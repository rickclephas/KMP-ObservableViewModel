package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.CoroutineScope
import platform.darwin.NSObject

/**
 * Implementation of [ViewModelScope] for Apple platforms.
 * @property coroutineScope The [CoroutineScope] associated with the [ViewModel].
 */
internal class NativeViewModelScope internal constructor(
    val coroutineScope: CoroutineScope
): NSObject(), ViewModelScope {

    private val _subscriptionCount = SubscriptionCount()
    override fun subscriptionCount(): SubscriptionCount = _subscriptionCount

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
