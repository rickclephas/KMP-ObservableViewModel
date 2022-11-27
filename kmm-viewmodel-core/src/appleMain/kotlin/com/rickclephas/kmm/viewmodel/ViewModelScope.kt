package com.rickclephas.kmm.viewmodel

import com.rickclephas.kmm.viewmodel.cinterop.KMMVMViewModelScopeProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

/**
 * Holds the [CoroutineScope] of a [KMMViewModel].
 * @see coroutineScope
 */
public actual typealias ViewModelScope = KMMVMViewModelScopeProtocol

/**
 * Gets the [CoroutineScope] associated with the [KMMViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = asImpl().coroutineScope

/**
 * Casts `this` [ViewModelScope] to a [ViewModelScopeImpl].
 */
@InternalKMMViewModelApi
public inline fun ViewModelScope.asImpl(): ViewModelScopeImpl = this as ViewModelScopeImpl

/**
 * Implementation of [ViewModelScope].
 */
@InternalKMMViewModelApi
public class ViewModelScopeImpl internal constructor(
    private val viewModelRef: WeakReference<KMMViewModel>
): NSObject(), ViewModelScope {

    /**
     * The [CoroutineScope] associated with the [KMMViewModel].
     */
    public val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _subscriptionCount = MutableStateFlow(0)
    /**
     * A [StateFlow] that emits the number of subscribers to the [KMMViewModel].
     */
    public val subscriptionCount: StateFlow<Int> = _subscriptionCount.asStateFlow()

    override fun increaseSubscriptionCount() {
        _subscriptionCount.update { it + 1 }
    }

    override fun decreaseSubscriptionCount() {
        _subscriptionCount.update { it - 1 }
    }

    private var sendObjectWillChange: (() -> Unit)? = null

    override fun setSendObjectWillChange(sendObjectWillChange: () -> Unit) {
        if (this.sendObjectWillChange != null) {
            throw IllegalStateException("KMMViewModel can't be wrapped more than once")
        }
        this.sendObjectWillChange = sendObjectWillChange
    }

    /**
     * Invokes the object will change listener set by [setSendObjectWillChange].
     */
    public fun sendObjectWillChange() {
        sendObjectWillChange?.invoke()
    }

    override fun cancel() {
        coroutineScope.cancel()
        viewModelRef.value?.onCleared()
    }
}
