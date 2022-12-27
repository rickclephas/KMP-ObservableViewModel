package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

public actual inline fun <reified T, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    vararg states: State<T>,
    crossinline calculation: (Array<T>) -> R
): State<R> = combine(*states) { calculation(it) }
    .distinctUntilChanged(policy::equivalent)
    .collectAsState(viewModelScope, calculation(states.map { it.value }.toTypedArray()))

public actual inline fun <reified T, R> combine(
    viewModelScope: ViewModelScope,
    vararg states: State<T>,
    crossinline calculation: (Array<T>) -> R
): State<R> = combine(*states) { calculation(it) }
    .collectAsState(viewModelScope, calculation(states.map { it.value }.toTypedArray()))

public actual inline fun <T1, T2, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    state1: State<T1>,
    state2: State<T2>,
    crossinline calculation: (T1, T2) -> R
): State<R> = combine(state1, state2) { value1, value2 -> calculation(value1, value2) }
    .distinctUntilChanged(policy::equivalent)
    .collectAsState(viewModelScope, calculation(state1.value, state2.value))

public actual inline fun <T1, T2, R> combine(
    viewModelScope: ViewModelScope,
    state1: State<T1>,
    state2: State<T2>,
    crossinline calculation: (T1, T2) -> R
): State<R> = combine(state1, state2) { value1, value2 -> calculation(value1, value2) }
    .collectAsState(viewModelScope, calculation(state1.value, state2.value))

public actual inline fun <T1, T2, T3, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    state1: State<T1>,
    state2: State<T2>,
    state3: State<T3>,
    crossinline calculation: (T1, T2, T3) -> R
): State<R> = combine(state1, state2, state3) { value1, value2, value3 -> calculation(value1, value2, value3) }
    .distinctUntilChanged(policy::equivalent)
    .collectAsState(viewModelScope, calculation(state1.value, state2.value, state3.value))

public actual inline fun <T1, T2, T3, R> combine(
    viewModelScope: ViewModelScope,
    state1: State<T1>,
    state2: State<T2>,
    state3: State<T3>,
    crossinline calculation: (T1, T2, T3) -> R
): State<R> = combine(state1, state2, state3) { value1, value2, value3 -> calculation(value1, value2, value3) }
    .collectAsState(viewModelScope, calculation(state1.value, state2.value, state3.value))
