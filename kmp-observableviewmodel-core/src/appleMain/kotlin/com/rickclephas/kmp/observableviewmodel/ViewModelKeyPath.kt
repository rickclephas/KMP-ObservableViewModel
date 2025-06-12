package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMViewModelKeyPathProtocol
import kotlinx.coroutines.flow.StateFlow

/**
 * Sets the [KeyPath][KMPOVMViewModelKeyPathProtocol] of an `ObservableStateFlow`.
 * @throws IllegalArgumentException if `this` [StateFlow] isn't an `ObservableStateFlow`.
 */
@InternalKMPObservableViewModelApi
public fun <T> StateFlow<T>.setKeyPath(keyPath: KMPOVMViewModelKeyPathProtocol?) {
    requireObservableStateFlow(this).keyPath = keyPath
}

/**
 * Helper to emit [keyPath] access events through the [NativeViewModelScope].
 * @param keyPath The keyPath being accessed.
 * @param action The action accessing the keyPath value.
 */
internal inline fun <R> NativeViewModelScope.access(
    keyPath: KMPOVMViewModelKeyPathProtocol?,
    action: () -> R
): R {
    val publisher = publisher
    if (keyPath != null && publisher != null) {
        keyPath.access(publisher)
    }
    return action()
}

/**
 * Helper to emit [keyPath] willSet and didSet events through the [NativeViewModelScope].
 * @param keyPath The keyPath being set.
 * @param changed Indicates if it's likely the property will be changed.
 * False-positives are allowed, false-negatives aren't.
 * @param action The action setting the new keyPath value.
 */
internal inline fun <R> NativeViewModelScope.set(
    keyPath: KMPOVMViewModelKeyPathProtocol?,
    changed: Boolean,
    action: () -> R
): R {
    val publisher = publisher
    if (!changed || publisher == null) return action()
    if (keyPath != null) {
        keyPath.willSet(publisher)
    } else if (!requireKeyPaths) {
        publisher.send()
    }
    val result = action()
    keyPath?.didSet(publisher)
    return result
}
