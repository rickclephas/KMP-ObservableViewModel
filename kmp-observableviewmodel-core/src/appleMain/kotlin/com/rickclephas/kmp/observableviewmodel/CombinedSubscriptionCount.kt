package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

/**
 * A [StateFlow] that combines the subscription counts of a [NativeViewModelScope] and a [StateFlow].
 */
@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
internal class CombinedSubscriptionCount private constructor(
    private val viewModelScopeCount: StateFlow<Int>,
    private val stateFlowCount: StateFlow<Int>
): StateFlow<Int> {

    constructor(viewModelScope: NativeViewModelScope, stateFlow: MutableStateFlow<*>) : this(
        viewModelScopeCount = viewModelScope.subscriptionCount,
        stateFlowCount = stateFlow.subscriptionCount
    )

    override val value: Int
        get() = viewModelScopeCount.value + stateFlowCount.value

    override val replayCache: List<Int>
        get() = listOf(value)

    override suspend fun collect(collector: FlowCollector<Int>): Nothing {
        viewModelScopeCount.combine(stateFlowCount) { viewModelScopeCount, stateFlowCount ->
            viewModelScopeCount + stateFlowCount
        }.collect(collector)
        throw IllegalStateException("CombinedSubscriptionCount.collect should never complete")
    }
}
