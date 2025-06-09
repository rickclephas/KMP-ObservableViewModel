package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMViewModelScopeProtocol
import kotlinx.coroutines.CoroutineScope

/**
 * Holds the [CoroutineScope] of a [ViewModel].
 * @see coroutineScope
 */
public actual typealias ViewModelScope = KMPOVMViewModelScopeProtocol

/**
 * Creates a new [ViewModelScope] for the provided [coroutineScope].
 */
internal actual fun ViewModelScope(coroutineScope: CoroutineScope): ViewModelScope =
    NativeViewModelScope(coroutineScope)

/**
 * Gets the [CoroutineScope] associated with the [ViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = asNative().coroutineScope
