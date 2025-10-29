package com.rickclephas.kmp.observableviewmodel

import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMSubscriptionCountProtocol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.darwin.NSObject

/**
 * [KMPOVMSubscriptionCountProtocol] implementation that uses a [StateFlow] to store the subscription count.
 */
internal class SubscriptionCount: NSObject(), KMPOVMSubscriptionCountProtocol {

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow: StateFlow<Int> = _stateFlow.asStateFlow()

    override fun increase(): Unit = _stateFlow.update { it + 1 }
    override fun decrease(): Unit = _stateFlow.update { it - 1 }
}
