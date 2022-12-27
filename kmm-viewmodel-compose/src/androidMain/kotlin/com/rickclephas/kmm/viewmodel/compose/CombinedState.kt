package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.derivedStateOf
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual inline fun <reified T, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    vararg states: State<T>,
    crossinline calculation: (Array<T>) -> R
): State<R> = derivedStateOf(policy) { calculation(states.map { it.value }.toTypedArray()) }

public actual inline fun <reified T, R> combine(
    viewModelScope: ViewModelScope,
    vararg states: State<T>,
    crossinline calculation: (Array<T>) -> R
): State<R> = derivedStateOf { calculation(states.map { it.value }.toTypedArray()) }

public actual inline fun <T1, T2, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    state1: State<T1>,
    state2: State<T2>,
    crossinline calculation: (T1, T2) -> R
): State<R> = derivedStateOf(policy) { calculation(state1.value, state2.value) }

public actual inline fun <T1, T2, R> combine(
    viewModelScope: ViewModelScope,
    state1: State<T1>,
    state2: State<T2>,
    crossinline calculation: (T1, T2) -> R
): State<R> = derivedStateOf { calculation(state1.value, state2.value) }

public actual inline fun <T1, T2, T3, R> combine(
    viewModelScope: ViewModelScope,
    policy: SnapshotMutationPolicy<R>,
    state1: State<T1>,
    state2: State<T2>,
    state3: State<T3>,
    crossinline calculation: (T1, T2, T3) -> R
): State<R> = derivedStateOf(policy) { calculation(state1.value, state2.value, state3.value) }

public actual inline fun <T1, T2, T3, R> combine(
    viewModelScope: ViewModelScope,
    state1: State<T1>,
    state2: State<T2>,
    state3: State<T3>,
    crossinline calculation: (T1, T2, T3) -> R
): State<R> = derivedStateOf { calculation(state1.value, state2.value, state3.value) }
