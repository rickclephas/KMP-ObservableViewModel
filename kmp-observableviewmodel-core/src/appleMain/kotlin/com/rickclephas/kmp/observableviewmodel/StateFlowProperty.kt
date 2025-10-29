package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMPropertyProtocol
import kotlinx.coroutines.flow.StateFlow
import platform.darwin.NSObject

/**
 * A [Property][KMPOVMPropertyProtocol] that retrieves its value from a [StateFlow].
 */
internal class StateFlowProperty<out T>(
    private val stateFlow: StateFlow<T>
): NSObject(), KMPOVMPropertyProtocol {
    override fun value(): T = stateFlow.value
}
