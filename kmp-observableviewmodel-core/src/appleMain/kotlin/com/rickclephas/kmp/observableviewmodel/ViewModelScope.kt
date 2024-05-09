package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMViewModelScopeProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.darwin.NSObject

/**
 * Holds the [CoroutineScope] of a [ViewModel].
 * @see coroutineScope
 */
public actual typealias ViewModelScope = KMPOVMViewModelScopeProtocol

/**
 * Creates a new [ViewModelScope] for the provided [coroutineScope].
 */
internal actual fun ViewModelScope(coroutineScope: CoroutineScope): ViewModelScope =
    ViewModelScopeImpl(coroutineScope)

/**
 * Gets the [CoroutineScope] associated with the [ViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = asImpl().coroutineScope

/**
 * Casts `this` [ViewModelScope] to a [ViewModelScopeImpl].
 */
@InternalKMPObservableViewModelApi
public inline fun ViewModelScope.asImpl(): ViewModelScopeImpl = this as ViewModelScopeImpl

/**
 * Implementation of [ViewModelScope].
 * @property coroutineScope The [CoroutineScope] associated with the [ViewModel].
 */
@InternalKMPObservableViewModelApi
public class ViewModelScopeImpl internal constructor(
    public val coroutineScope: CoroutineScope
): NSObject(), ViewModelScope {

    private val _subscriptionCount = MutableStateFlow(0)
    /**
     * A [StateFlow] that emits the number of subscribers to the [ViewModel].
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
            throw IllegalStateException("ViewModel can't be wrapped more than once")
        }
        this.sendObjectWillChange = sendObjectWillChange
    }

    /**
     * Invokes the object will change listener set by [setSendObjectWillChange].
     */
    public fun sendObjectWillChange() {
        sendObjectWillChange?.invoke()
    }
}
