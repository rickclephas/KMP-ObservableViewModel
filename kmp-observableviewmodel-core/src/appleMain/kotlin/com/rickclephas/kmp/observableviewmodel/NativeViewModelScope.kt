package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMPublisherProtocol
import kotlinx.coroutines.CoroutineScope
import platform.darwin.NSObject

/**
 * Implementation of [ViewModelScope] for Apple platforms.
 * @property coroutineScope The [CoroutineScope] associated with the [ViewModel].
 */
internal class NativeViewModelScope(
    val coroutineScope: CoroutineScope
): NSObject(), ViewModelScope {

    private val _subscriptionCount = SubscriptionCount()
    override fun subscriptionCount(): SubscriptionCount = _subscriptionCount

    private var _publisher: KMPOVMPublisherProtocol? = null
    override fun publisher(): KMPOVMPublisherProtocol? = _publisher
    override fun setPublisher(publisher: KMPOVMPublisherProtocol?) {
        if (_publisher != null) throw IllegalStateException("ViewModel can't be initialized more than once")
        _publisher = publisher
    }
}

/**
 * Casts `this` [ViewModelScope] to a [NativeViewModelScope].
 */
internal inline fun ViewModelScope.asNative(): NativeViewModelScope = this as NativeViewModelScope
