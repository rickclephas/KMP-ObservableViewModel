package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.MutableState as ComposeMutableState
import androidx.compose.runtime.mutableStateOf as composeMutableStateOf
import androidx.compose.runtime.SnapshotMutationPolicy
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual interface MutableState<T>: State<T>, ComposeMutableState<T>

public actual fun <T : Any?> ViewModelScope.mutableStateOf(
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T> = MutableStateImpl(value, policy)

private class MutableStateImpl<T>(
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T>, ComposeMutableState<T> by composeMutableStateOf(value, policy)
